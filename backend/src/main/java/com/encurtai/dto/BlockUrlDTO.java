package com.encurtai.dto;

import jakarta.validation.constraints.NotBlank;

public record BlockUrlDTO(

    @NotBlank(message = "URL não pode ser vazia")
    String url,
    @NotBlank(message = "Tipo de bloqueio não pode ser vazio")
    String blockType

) {

}
