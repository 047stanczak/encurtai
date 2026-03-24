package com.encurtai.controller.api;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.UserChangePasswordDTO;
import com.encurtai.models.User;
import com.encurtai.services.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse<Object>> updateUser(
        @Valid @RequestBody UserChangePasswordDTO userChangePasswordDTO,
        @AuthenticationPrincipal User user){
        userService.updateUser(user, userChangePasswordDTO);
        return ResponseEntity.ok(ApiResponse.ok("Usuário atualizado com sucesso"));
    }
}
