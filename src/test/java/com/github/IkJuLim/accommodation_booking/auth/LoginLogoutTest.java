package com.github.IkJuLim.accommodation_booking.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.IkJuLim.accommodation_booking.dto.UserRequestDTO;
import com.github.IkJuLim.accommodation_booking.exception.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class LoginLogoutTest {

    @Autowired
    private MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("로그인 성공")
    void loginTest() throws Exception {
        UserRequestDTO.UserLogInDto userLoginDto = UserRequestDTO.UserLogInDto.builder()
                .email("test@gmail.com")
                .password("1234qwer!!")
                .build();

        mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("\"로그인에 성공했습니다\""));
    }

    @Test
    @DisplayName("로그인 실패 - 이메일")
    void loginTestFailEmail() throws Exception {
        UserRequestDTO.UserLogInDto userLoginDto = UserRequestDTO.UserLogInDto.builder()
                .email("test@gmail.com")
                .password("1234qwer!!")
                .build();

        mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginDto))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("이메일 또는 비밀번호가 일치하지 않습니다."));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호")
    void loginTestFailPassword() throws Exception {
        UserRequestDTO.UserLogInDto userLoginDto = UserRequestDTO.UserLogInDto.builder()
                .email("test@gmail.com")
                .password("1234qwer!!")
                .build();

        mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginDto))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("이메일 또는 비밀번호가 일치하지 않습니다."));
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logoutTest() throws Exception {

        mvc.perform(post("/api/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}