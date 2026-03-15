package com.encurtai.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.BlockUrlDTO;
import com.encurtai.models.User;
import com.encurtai.services.BlockUrlService;

@RestController
@RequestMapping("/api/blockurl")
public class BlockUrlController {

    private final BlockUrlService blockUrlService;

    public BlockUrlController(BlockUrlService blockUrlService) {
        this.blockUrlService = blockUrlService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> blockUrl(
        @AuthenticationPrincipal User user,
        @RequestBody BlockUrlDTO blockUrlDTO
    ){
        
        blockUrlService.blockUrl(blockUrlDTO);

        return ResponseEntity
        .status(201)
        .body(ApiResponse.created("URL bloqueada com sucesso"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getBlockedUrl(){
        List<BlockUrlDTO> urls = blockUrlService.getBlockedUrls();
        return ResponseEntity.ok(ApiResponse.ok("URLS bloqueadas", urls));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBlockedUrl(@PathVariable Long id){
        blockUrlService.deleteBlockedUrl(id);
        return ResponseEntity.noContent().build();
    }
}
