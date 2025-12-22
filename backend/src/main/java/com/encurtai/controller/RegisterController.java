package com.encurtai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.RegisterDTO;
import com.encurtai.services.RegisterService;

// TODO: Use RegisterController
@RestController
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterDTO register){
        registerService.register(register);
        return ResponseEntity.ok(ApiResponse.ok("Registro feito com sucesso"));
    }
}
