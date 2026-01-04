package com.encurtai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.UrlDTO;
import com.encurtai.dto.UrlGeneratorDTO;
import com.encurtai.models.User;
import com.encurtai.services.UrlGeneratorService;
import com.encurtai.services.UrlService;

@RestController
public class ShortUrlController {
    @Autowired
    private UrlGeneratorService urlGeneratorService;

    @Autowired
    private UrlService urlService;

    @PostMapping("/generate-url")
    public ResponseEntity<ApiResponse<Object>> urlGenerator(@RequestBody UrlGeneratorDTO urlGeneratorDTO, @AuthenticationPrincipal User user){
        String hash = urlGeneratorService.generator(urlGeneratorDTO, user);
        return ResponseEntity.ok(ApiResponse.urlGenerated("Url gerada com sucesso", hash));
    }

    @GetMapping("/my-urls")
    public List<UrlDTO> myUrls(@AuthenticationPrincipal User user){

        List<UrlDTO> urls = urlService.getUrls(user);

        return urls;
    }
}
