package com.github.IkJuLim.accommodation_booking.config.auth;

import com.github.IkJuLim.accommodation_booking.domain.User;
import com.github.IkJuLim.accommodation_booking.domain.enums.UserRole;
import com.github.IkJuLim.accommodation_booking.dto.UserResponseDTO;
import com.github.IkJuLim.accommodation_booking.dto.auth.GoogleResponse;
import com.github.IkJuLim.accommodation_booking.dto.auth.KakaoResponse;
import com.github.IkJuLim.accommodation_booking.dto.auth.OAuth2Response;
import com.github.IkJuLim.accommodation_booking.exception.code.ErrorStatus;
import com.github.IkJuLim.accommodation_booking.exception.handler.UserExceptionHandler;
import com.github.IkJuLim.accommodation_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        userRepository.findByUsername(username).orElseThrow(() -> new UserExceptionHandler(ErrorStatus.USER_ALREADY_EXIST));

        User user = User.builder()
                .email(oAuth2Response.getEmail())
                .username(username)
                .userRole(UserRole.GUEST)
                .build();

        userRepository.save(user);

        UserResponseDTO.OauthUserDTO userDTO = UserResponseDTO.OauthUserDTO.builder()
                .email(oAuth2Response.getEmail())
                .username(username)
                .role("GUEST")
                .build();

        return new CustomOAuth2User(userDTO);
    }
}