package com.github.IkJuLim.accommodation_booking.service;

import com.github.IkJuLim.accommodation_booking.domain.User;
import com.github.IkJuLim.accommodation_booking.domain.enums.UserRoll;
import com.github.IkJuLim.accommodation_booking.dto.UserRequestDTO;
import com.github.IkJuLim.accommodation_booking.exception.code.ErrorStatus;
import com.github.IkJuLim.accommodation_booking.exception.handler.MemberExceptionHandler;
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
        if (userSignUpDto.getUserRoll().equals(UserRoll.ADMIN)) {
            throw new MemberExceptionHandler(ErrorStatus.USER_INVALID_ROLL);
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(passwordEncoder.encode(userSignUpDto.getPassword()))
                .nickname(userSignUpDto.getNickname())
                .userRoll(userSignUpDto.getUserRoll())
                .status(1)
                .build();

        if (userRepository.existsByEmail(user.getEmail())) {
            return userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new MemberExceptionHandler(ErrorStatus.USER_NOT_FOUND));
        } else {
            return userRepository.save(user);
        }
    }
}
