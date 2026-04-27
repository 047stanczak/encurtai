package com.encurtai.controller.api;

import com.encurtai.dto.UserDTO;
import com.encurtai.exception.EmailAlreadyExistsException;
import com.encurtai.exception.GlobalExceptionHandler;
import com.encurtai.exception.InvalidCredentialsException;
import com.encurtai.exception.UserNotFoundException;
import com.encurtai.repository.UserRepository;
import com.encurtai.security.TokenSecurity;
import com.encurtai.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
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

    private final UserDTO userDTO = new UserDTO(
            "user@email.com",
            "userPassword"
    );

    @Test
    void shouldReturnTokenAndSetCookieOnLogin() throws Exception {
        when(userService.login(any(UserDTO.class)))
                .thenReturn("jwt-token");

        String json = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Login realizado com sucesso"))
                .andExpect(jsonPath("$.data").value("jwt-token"))
                .andExpect(header().string(HttpHeaders.SET_COOKIE, allOf(
                        containsString("token=jwt-token"),
                        containsString("Path=/"),
                        containsString("HttpOnly"),
                        containsString("SameSite=Lax")
                )));

        verify(userService).login(any(UserDTO.class));
    }

    @Test
    void shouldReturn404WhenUserNotFoundOnLogin() throws Exception {
        when(userService.login(any(UserDTO.class)))
                .thenThrow(new UserNotFoundException("Usuário não encontrado"));

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Usuário não encontrado"));
    }

    @Test
    void shouldReturn401WhenInvalidPasswordOnLogin() throws Exception {
        when(userService.login(any(UserDTO.class)))
                .thenThrow(new InvalidCredentialsException("Senha inválida"));

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message").value("Senha inválida"));
    }

    @Test
    void shouldRegisterUser() throws Exception {
        String json = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Registro feito com sucesso"));

        verify(userService).register(any(UserDTO.class));
    }

    @Test
    void shouldReturn400WhenEmailAlreadyExistsOnRegister() throws Exception {
        doThrow(new EmailAlreadyExistsException("Email já cadastrado"))
                .when(userService).register(any(UserDTO.class));

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Email já cadastrado"));
    }

    @Test
    void shouldReturn400WhenPayloadFailsValidation() throws Exception {
        var invalid = new UserDTO("not-an-email", "12345");
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldClearTokenCookieOnLogout() throws Exception {
        mockMvc.perform(post("/api/logout"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.SET_COOKIE, allOf(
                        containsString("token="),
                        containsString("Max-Age=0"),
                        containsString("Path=/"),
                        containsString("HttpOnly"),
                        containsString("SameSite=Lax")
                )));
    }
}
