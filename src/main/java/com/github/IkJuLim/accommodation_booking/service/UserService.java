package com.github.IkJuLim.accommodation_booking.service;

import com.github.IkJuLim.accommodation_booking.domain.User;
import com.github.IkJuLim.accommodation_booking.domain.enums.UserRole;
import com.github.IkJuLim.accommodation_booking.dto.UserRequestDTO;
import com.github.IkJuLim.accommodation_booking.exception.code.ErrorStatus;
import com.github.IkJuLim.accommodation_booking.exception.handler.UserExceptionHandler;
import com.github.IkJuLim.accommodation_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param userSignUpDto UserRequestDTO.UserSignUpDto
     * @return User
     */
    @Transactional
    public User signUp(UserRequestDTO.UserSignUpDto userSignUpDto) {
        if (userSignUpDto.getUserRole().equals(UserRole.ADMIN)) {
            throw new UserExceptionHandler(ErrorStatus.USER_INVALID_ROLE);
        }

        if (userRepository.existsByEmail(userSignUpDto.getEmail())) {
            return userRepository.findByEmail(userSignUpDto.getEmail()).orElseThrow(() -> new UserExceptionHandler(ErrorStatus.USER_NOT_FOUND));
        } else {
            User user = User.builder()
                    .email(userSignUpDto.getEmail())
                    .password(passwordEncoder.encode(userSignUpDto.getPassword()))
                    .username(userSignUpDto.getUsername())
                    .userRole(userSignUpDto.getUserRole())
                    .status(1)
                    .build();

            return userRepository.save(user);
        }
    }
}
