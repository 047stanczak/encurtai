package com.encurtai.dto;

import jakarta.validation.constraints.NotBlank;

public record UrlGeneratorDTO(
    @NotBlank(message = "URL não pode estar vazia")
    String url) {}
