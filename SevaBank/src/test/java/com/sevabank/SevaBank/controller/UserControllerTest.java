package com.sevabank.SevaBank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.service.UserServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServices userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUser() throws Exception {

        RegisterReqDto request = new RegisterReqDto();
        request.setName("Vishal");
        request.setEmail("vishal@gmail.com");
        request.setPassword("Password@123");
        request.setAge(22);

        UserResponseDto response = new UserResponseDto();
        response.setId(1L);
        response.setName("Vishal");
        response.setEmail("vishal@gmail.com");

        when(userService.createUser(any(RegisterReqDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Vishal"))
                .andExpect(jsonPath("$.email").value("vishal@gmail.com"));
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {

        LoginReqDto request = new LoginReqDto();
        request.setAccNo(1001L);
        request.setEmail("vishal@gmail.com");
        request.setPassword("Password@123");

        when(userService.login(request))
                .thenReturn(true);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged in successfully!"));
    }

    @Test
    void shouldReturnInvalidCredentials() throws Exception {

        LoginReqDto request = new LoginReqDto();
        request.setAccNo(1001L);
        request.setEmail("wrong@gmail.com");
        request.setPassword("wrong");

        when(userService.login(request))
                .thenReturn(false);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid email or password"));
    }

    @Test
    void shouldLoginV1Successfully() throws Exception {

        LoginReqDto request = new LoginReqDto();
        request.setAccNo(1001L);
        request.setEmail("vishal@gmail.com");
        request.setPassword("Password@123");

        when(userService.loginV1(request))
                .thenReturn(true);

        mockMvc.perform(post("/api/user/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged in successfully!"));
    }

    @Test
    void shouldReturnInvalidCredentialsForLoginV1() throws Exception {

        LoginReqDto request = new LoginReqDto();
        request.setAccNo(1001L);
        request.setEmail("wrong@gmail.com");
        request.setPassword("wrong");

        when(userService.loginV1(request))
                .thenReturn(false);

        mockMvc.perform(post("/api/user/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid email or password"));
    }
}