package com.encurtai.dto;

import jakarta.validation.constraints.NotBlank;

public record UserChangePasswordDTO(

    @NotBlank(message = "A senha não pode estar vazia")
    String password,

    @NotBlank(message = "A senha não pode estar vazia")
    String newPassword

) {}
