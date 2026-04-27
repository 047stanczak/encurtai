package com.encurtai.controller.redirect;

import com.encurtai.exception.GlobalExceptionHandler;
import com.encurtai.exception.UrlByHashNotFoundException;
import com.encurtai.repository.UserRepository;
import com.encurtai.security.TokenSecurity;
import com.encurtai.services.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RedirectController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class RedirectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UrlService urlService;

    @MockitoBean
    TokenSecurity tokenSecurity;

    @MockitoBean
    UserRepository userRepository;

    @Test
    void shouldRedirectToOriginalUrl() throws Exception {
        when(urlService.getUrl("abc123"))
                .thenReturn("https://www.google.com/");

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "https://www.google.com/"));

        verify(urlService).getUrl(eq("abc123"));
    }

    @Test
    void shouldReturn404WhenHashNotFound() throws Exception {
        when(urlService.getUrl("missing"))
                .thenThrow(new UrlByHashNotFoundException("Url não encontrada"));

        mockMvc.perform(get("/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Url não encontrada"));

        verify(urlService).getUrl(eq("missing"));
    }
}
