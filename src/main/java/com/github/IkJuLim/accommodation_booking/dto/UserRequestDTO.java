package com.github.IkJuLim.accommodation_booking.dto;

import com.github.IkJuLim.accommodation_booking.domain.enums.UserRoll;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDTO {

    @Builder
    @Getter
    public static class UserSignUpDto {

        //email
        @NotBlank(message = "아이디를 입력해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
                message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        //비밀번호
        @Setter
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
                message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
        private String password;

        /**
         * 사용 가능: GUEST, HOST
         * 사용 불가: ADMIN
         */
        private UserRoll userRoll;

        //이름
        @NotBlank(message = "이름을 입력해주세요")
        @Size(min = 2, message = "사용자 이름이 너무 짧습니다.")
        @Pattern(regexp = "^[가-힣A-Za-z0-9]+$", message = "사용자 이름은 한글, 알파벳, 숫자만 입력해주세요.")
        private String nickname;
    }
}
