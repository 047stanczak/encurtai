package com.encurtai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.UserDTO;
import com.encurtai.services.RegisterService;

@RestController
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody UserDTO user){
        registerService.register(user);
        return ResponseEntity.ok(ApiResponse.ok("Registro feito com sucesso"));
    }
}
