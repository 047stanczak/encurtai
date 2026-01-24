package com.encurtai.controller.api;

import com.encurtai.dto.UserDTO;
import com.encurtai.repository.UserRepository;
import com.encurtai.security.TokenSecurity;
import com.encurtai.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserService userService;

    @MockitoBean
    TokenSecurity tokenSecurity;

    @MockitoBean
    UserRepository userRepository;

    UserDTO userDTO = new UserDTO(
            "user@email.com",
            "userPassword"
    );

    @Test
    void shouldReturnToken() throws Exception {
        when(userService.login(any(UserDTO.class)))
                .thenReturn("token");

        String json = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login realizado com sucesso"))
                .andExpect(jsonPath("$.data").value("token"));

    }


}
