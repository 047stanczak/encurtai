package com.encurtai.controller.redirect;

import com.encurtai.repository.UserRepository;
import com.encurtai.security.TokenSecurity;
import com.encurtai.services.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RedirectController.class)
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
    void shouldRedirectToOriginalUrl() throws Exception{
        when(urlService.getUrl("abc123"))
                .thenReturn("https://www.google.com/");

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "https://www.google.com/"));
    }

}

