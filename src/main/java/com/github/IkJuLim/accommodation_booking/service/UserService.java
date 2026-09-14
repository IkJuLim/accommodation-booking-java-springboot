package com.github.IkJuLim.accommodation_booking.service;

import com.github.IkJuLim.accommodation_booking.domain.User;
import com.github.IkJuLim.accommodation_booking.dto.UserRequestDTO;
import com.github.IkJuLim.accommodation_booking.exception.code.ErrorStatus;
import com.github.IkJuLim.accommodation_booking.exception.handler.MemberExceptionHandler;
import com.github.IkJuLim.accommodation_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * @param userSignUpDto
     * @return
     */
    @Transactional
    public User signUp(UserRequestDTO.UserSignUpDto userSignUpDto) {
        User user = User.builder()
                .nickname(userSignUpDto.getNickname())
                .email(userSignUpDto.getEmail())
                .status(1)
                .build();
        if(userRepository.existsByEmail(user.getEmail())){
            return userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new MemberExceptionHandler(ErrorStatus.USER_NOT_FOUND));
        }
        else{
            return userRepository.save(user);
        }
    }
}
