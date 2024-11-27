package com.morethandaily.morethandaily;

import com.morethandaily.morethandaily.controller.UserController;
import com.morethandaily.morethandaily.entity.User;
import com.morethandaily.morethandaily.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUser() throws Exception {
        // Mock 데이터 준비
        User mockUser = new User(1L, "John Doe", "john.doe@example.com", "password123");

        // UserService의 register 메서드 Mocking
        when(userService.register(anyString(), anyString(), anyString())).thenReturn(mockUser);

        // 회원가입 API 호출 및 검증
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John Doe",
                                    "email": "john.doe@example.com",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isOk()) // 200 OK 응답 확인
                .andExpect(jsonPath("$.id").value(1L)) // JSON 응답의 ID 필드 확인
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testLoginUser() throws Exception {
        // Mock 데이터 준비
        User mockUser = new User(1L, "John Doe", "john.doe@example.com", "password123");

        // UserService의 login 메서드 Mocking
        when(userService.login(anyString(), anyString())).thenReturn(mockUser);

        // 로그인 API 호출 및 검증
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "john.doe@example.com",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isOk()) // 200 OK 응답 확인
                .andExpect(jsonPath("$.id").value(1L)) // JSON 응답의 ID 필드 확인
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }
}
