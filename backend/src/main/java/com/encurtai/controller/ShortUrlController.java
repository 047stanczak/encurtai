package com.encurtai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.UrlGeneratorDTO;
import com.encurtai.models.User;
import com.encurtai.services.UrlGeneratorService;

@RestController
public class ShortUrlController {
    @Autowired
    UrlGeneratorService urlGeneratorService;

    @PostMapping("/generate-url")
    public ResponseEntity<ApiResponse<Object>> urlGenerator(@RequestBody UrlGeneratorDTO urlGeneratorDTO, @AuthenticationPrincipal User user){
        String hash = urlGeneratorService.generator(urlGeneratorDTO, user);
        return ResponseEntity.ok(ApiResponse.urlGenerated("Url gerada com sucesso", hash));
    }
}
