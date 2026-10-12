package com.github.IkJuLim.accommodation_booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    @Builder
    @Getter
    @Setter
    public static class OauthUserDTO {

        private String email;

        private String role;

        private String username;
    }
}
