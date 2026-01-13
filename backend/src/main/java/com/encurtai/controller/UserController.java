package com.encurtai.controller;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.UserDTO;
import com.encurtai.models.User;
import com.encurtai.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody UserDTO user){
        String token = userService.login(user);
        return ResponseEntity.ok(ApiResponse.ok("Login realizado com sucesso", token));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody UserDTO user){
        userService.register(user);
        return ResponseEntity.ok(ApiResponse.ok("Registro feito com sucesso"));
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse<Object>> updateUser(@RequestBody UserDTO userDTO ,@AuthenticationPrincipal User user){

        userService.updateUser(user, userDTO);

        return ResponseEntity.ok(ApiResponse.ok("Usuário atualizado"));
    }
}
