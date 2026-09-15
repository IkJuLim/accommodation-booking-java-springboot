package com.github.IkJuLim.accommodation_booking.controller;

import com.github.IkJuLim.accommodation_booking.domain.User;
import com.github.IkJuLim.accommodation_booking.dto.UserRequestDTO;
import com.github.IkJuLim.accommodation_booking.dto.UserResponseDTO;
import com.github.IkJuLim.accommodation_booking.exception.ApiResponse;
import com.github.IkJuLim.accommodation_booking.exception.code.SuccessStatus;
import com.github.IkJuLim.accommodation_booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(version = "1")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ApiResponse<UserResponseDTO.UserSignUpDto> joinMember(@RequestBody UserRequestDTO.UserSignUpDto joinMemberDTO){
        User user = userService.signUp(joinMemberDTO);

        return ApiResponse.of(SuccessStatus.USER_JOIN, UserResponseDTO.UserSignUpDto.builder()
                .id(user.getId())
                .created_at(user.getCreatedAt())
                .build());
    }
}
