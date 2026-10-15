package com.github.IkJuLim.accommodation_booking.auth;

import com.github.IkJuLim.accommodation_booking.config.auth.CustomOAuth2User;
import com.github.IkJuLim.accommodation_booking.config.auth.CustomOAuth2UserService;
import com.github.IkJuLim.accommodation_booking.domain.User;
import com.github.IkJuLim.accommodation_booking.exception.handler.UserExceptionHandler;
import com.github.IkJuLim.accommodation_booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestOperations;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestOperations restOperations;

    private CustomOAuth2UserService customOAuth2UserService;

    @SuppressWarnings("unchecked")
    private void stubUserInfoResponse(Map<String, Object> attributes) {
        given(restOperations.exchange(any(RequestEntity.class), any(ParameterizedTypeReference.class)))
                .willReturn(ResponseEntity.ok(attributes));
    }

    @BeforeEach
    void setUp() {
        customOAuth2UserService = new CustomOAuth2UserService(userRepository);
        customOAuth2UserService.setRestOperations(restOperations);
    }

    @Test
    @DisplayName("OAuth2 가입 성공 - 신규 유저면 저장된다")
    void loadUser_NewUser_SavesUser() {
        // given
        stubUserInfoResponse(Map.of(
                "sub", "1234567890",
                "email", "newuser@gmail.com"
        ));
        given(userRepository.findByUsername("google 1234567890")).willReturn(Optional.empty());

        // when
        OAuth2User result = customOAuth2UserService.loadUser(googleUserRequest());

        // then
        assertThat(result).isInstanceOf(CustomOAuth2User.class);
        assertThat(((CustomOAuth2User) result).getUsername()).isEqualTo("google 1234567890");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        then(userRepository).should().save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("newuser@gmail.com");
        assertThat(captor.getValue().getUsername()).isEqualTo("google 1234567890");
    }

    @Test
    @DisplayName("OAuth2 가입 실패 - 이미 가입된 유저면 예외가 발생하고 저장되지 않는다")
    void loadUser_ExistingUser_ThrowsAndDoesNotSaveAgain() {
        // given
        stubUserInfoResponse(Map.of(
                "sub", "1234567890",
                "email", "existing@gmail.com"
        ));
        given(userRepository.findByUsername("google 1234567890"))
                .willReturn(Optional.of(User.builder().username("google 1234567890").build()));

        // when & then
        assertThatThrownBy(() -> customOAuth2UserService.loadUser(googleUserRequest()))
                .isInstanceOf(UserExceptionHandler.class);

        then(userRepository).should(never()).save(any());
    }

    private OAuth2UserRequest googleUserRequest() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("google")
                .clientId("client-id")
                .clientSecret("client-secret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080/login/oauth2/code/google")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://oauth2.googleapis.com/token")
                .userInfoUri("https://openidconnect.googleapis.com/v1/userinfo")
                .userNameAttributeName("sub")
                .clientName("google")
                .build();

        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                "access-token",
                Instant.now(),
                Instant.now().plusSeconds(3600));

        return new OAuth2UserRequest(clientRegistration, accessToken);
    }
}