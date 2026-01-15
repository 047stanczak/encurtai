package com.encurtai.controller.api;

import com.encurtai.api.ApiResponse;
import com.encurtai.dto.UserDTO;
import com.encurtai.models.User;
import com.encurtai.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse<Object>> updateUser(@RequestBody UserDTO userDTO ,@AuthenticationPrincipal User user){

        userService.updateUser(user, userDTO);

        return ResponseEntity.ok(ApiResponse.ok("Usuário atualizado com sucesso"));
    }
}
