package com.encurtai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("{hash}")
    public ResponseEntity<Void> url(@PathVariable String hash){

        String originalUrl = urlService.getUrl(hash);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }

    @PostMapping("/url")
    public ResponseEntity<ApiResponse<Object>> urlGenerator(@RequestBody UrlGeneratorDTO urlGeneratorDTO, @AuthenticationPrincipal User user){
        String hash = urlGeneratorService.generator(urlGeneratorDTO.url(), user);
        return ResponseEntity.ok(ApiResponse.urlGenerated("Url gerada com sucesso", hash));
    }

    @GetMapping("/urls")
    public List<UrlDTO> myUrls(@AuthenticationPrincipal User user){
        List<UrlDTO> urls = urlService.getUrls(user);
        return urls;
    }

    @DeleteMapping("/url/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUrl(@PathVariable Long id, @AuthenticationPrincipal User user){
        urlService.deleteUrl(id, user);
        return ResponseEntity
                .status(204)
                .body(ApiResponse.ok("URL excluída com sucesso"));
    }
}
