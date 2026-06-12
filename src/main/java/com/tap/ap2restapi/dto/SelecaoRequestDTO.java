package com.tap.ap2restapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SelecaoRequestDTO {
    @NotBlank(message = "O nome do país não pode estar vazio")
    private String nomePais;

    @NotBlank(message = "O técnico não pode estar vazio")
    private String tecnico;

    private Integer rankingFifa;
}
