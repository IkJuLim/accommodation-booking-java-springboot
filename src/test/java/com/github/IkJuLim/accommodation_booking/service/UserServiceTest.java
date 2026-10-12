package com.github.IkJuLim.accommodation_booking.service;

import com.github.IkJuLim.accommodation_booking.domain.User;
import com.github.IkJuLim.accommodation_booking.domain.enums.UserRole;
import com.github.IkJuLim.accommodation_booking.dto.UserRequestDTO;
import com.github.IkJuLim.accommodation_booking.exception.handler.UserExceptionHandler;
import com.github.IkJuLim.accommodation_booking.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("회원 서비스 SignUp 성공")
    @Transactional
    void userSignUpTest() {
        // given
        UserRequestDTO.UserSignUpDto userSignUpDto = new UserRequestDTO.UserSignUpDto();
        userSignUpDto.setEmail(UUID.randomUUID() + "@gmail.com");
        userSignUpDto.setPassword("1234");
        userSignUpDto.setUserRole(UserRole.GUEST);
        userSignUpDto.setUsername("Test1");

        User user = User.builder()
                .id(1L)
                .email(userSignUpDto.getEmail())
                .password("encoded_password")
                .userRole(userSignUpDto.getUserRole())
                .username(userSignUpDto.getUsername())
                .build();

        given(passwordEncoder.encode(any())).willReturn("encoded_password");
        given(userRepository.save(any(User.class))).willReturn(user);

        // when & then
        Assertions.assertThatCode(() -> userService.signUp(userSignUpDto))
                .doesNotThrowAnyException();

        then(userRepository).should(times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("회원 서비스 SignUp 실패 - 잘못된 Email 형식")
    void userSignUpTest_Fail_Email() {
        // given
        String invalidEmail = UUID.randomUUID().toString(); // "@domain.com" 형식이 빠진 올바르지 않은 이메일

        UserRequestDTO.UserSignUpDto userSignUpDto = new UserRequestDTO.UserSignUpDto();
        userSignUpDto.setEmail(invalidEmail);
        userSignUpDto.setPassword("1234");
        userSignUpDto.setUserRole(UserRole.GUEST);
        userSignUpDto.setUsername("Test1");
        // when
        Set<ConstraintViolation<UserRequestDTO.UserSignUpDto>> violations = validator.validate(userSignUpDto);

        // then
        Assertions.assertThat(violations).isNotEmpty();
        Assertions.assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString())
                .contains("email");
    }

    @Test
    @DisplayName("회원 서비스 SignUp 실패 - 닉네임에 특수문자 포함 불가")
    void userSignUpTest_Fail_UsernameSpecialCharacters() {
        // given
        String invalidUsername = "Test@User!"; // 특수문자 포함

        UserRequestDTO.UserSignUpDto userSignUpDto = new UserRequestDTO.UserSignUpDto();
        userSignUpDto.setEmail(UUID.randomUUID() + "@gmail.com");
        userSignUpDto.setPassword("1234");
        userSignUpDto.setUserRole(UserRole.GUEST);
        userSignUpDto.setUsername(invalidUsername);

        // when
        Set<ConstraintViolation<UserRequestDTO.UserSignUpDto>> violations = validator.validate(userSignUpDto);

        // then
        Assertions.assertThat(violations).isNotEmpty();
        Assertions.assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString())
                .contains("username");
    }

    @Test
    @DisplayName("회원 서비스 SignUp 실패 - ADMIN Role 가입 불가 예외 발생")
    void userSignUpTest_Fail_AdminRoleNotAllowed() {
        // given
        UserRequestDTO.UserSignUpDto adminSignUpDto = new UserRequestDTO.UserSignUpDto();
        adminSignUpDto.setEmail(UUID.randomUUID() + "@gmail.com");
        adminSignUpDto.setPassword("password123");
        adminSignUpDto.setUserRole(UserRole.ADMIN);
        adminSignUpDto.setUsername("AdminUser");

        // when & then
        Assertions.assertThatThrownBy(() -> userService.signUp(adminSignUpDto))
                .isInstanceOf(UserExceptionHandler.class);

        // DB 저장 로직이 호출되지 않았는지 검증
        then(userRepository).should(never()).save(any());
    }
}