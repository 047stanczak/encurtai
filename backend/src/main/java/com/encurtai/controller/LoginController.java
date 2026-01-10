package com.encurtai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.UserDTO;
import com.encurtai.services.LoginService;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody UserDTO user){
        String token = loginService.login(user);
        return ResponseEntity.ok(ApiResponse.ok("Login realizado com sucesso", token));
    }
}
