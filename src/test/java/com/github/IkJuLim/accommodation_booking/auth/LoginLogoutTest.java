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

//    @Autowired
//    private MockMvc mvc;
//
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    @DisplayName("로그인 성공")
//    void loginTest() throws Exception {
//        UserRequestDTO.UserLogInDto userLoginDto = new UserRequestDTO.UserLogInDto();
//        userLoginDto.setEmail("test@gmail.com");
//        userLoginDto.setPassword("1234qwer!!");
//
//        mvc.perform(post("/api/v1/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userLoginDto))
//                )
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("로그인 실패 - 이메일")
//    void loginTestFailEmail() throws Exception {
//        UserRequestDTO.UserLogInDto userLoginDto = new UserRequestDTO.UserLogInDto();
//        userLoginDto.setEmail("testEmail");
//        userLoginDto.setPassword("1234qwer!!");
//
//        mvc.perform(post("/api/v1/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userLoginDto))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("로그인 실패 - 비밀번호")
//    void loginTestFailPassword() throws Exception {
//        UserRequestDTO.UserLogInDto userLoginDto = new UserRequestDTO.UserLogInDto();
//        userLoginDto.setEmail("test@gmail.com");
//        userLoginDto.setPassword("1234");
//
//        mvc.perform(post("/api/v1/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userLoginDto))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("로그아웃 성공")
//    void logoutTest() throws Exception {
//
//        mvc.perform(post("/api/logout")
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk());
//    }
}