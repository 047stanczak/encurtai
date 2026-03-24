package com.encurtai.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
    @NotBlank(message = "Email não pode estar vazio")
    @Email(message = "Email inválido")
    String email,

    @NotBlank(message = "Senha não pode estar vazia")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    String password) {}
