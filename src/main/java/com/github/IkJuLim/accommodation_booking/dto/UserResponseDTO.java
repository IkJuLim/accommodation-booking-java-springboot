package com.github.IkJuLim.accommodation_booking.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserResponseDTO {

    @Builder
    @Getter
    public static class UserSignUpDto {

        //id
        private Long id;

        //created_at
        private LocalDateTime created_at;
    }
}
