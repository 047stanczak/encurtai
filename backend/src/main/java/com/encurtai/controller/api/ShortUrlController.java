package com.encurtai.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api")
public class ShortUrlController {
    private final UrlGeneratorService urlGeneratorService;
    private final UrlService urlService;

    public ShortUrlController(UrlGeneratorService urlGeneratorService, UrlService urlService) {
        this.urlGeneratorService = urlGeneratorService;
        this.urlService = urlService;
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
