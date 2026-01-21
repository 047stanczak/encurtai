package com.encurtai.controller.api;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.UserDTO;
import com.encurtai.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

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
}
