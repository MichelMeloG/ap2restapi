package com.tap.ap2restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PartidaRequestDTO {
    @NotNull(message = "A data não pode ser nula")
    private LocalDate data;

    @NotBlank(message = "O estádio não pode estar vazio")
    private String estadio;

    @NotBlank(message = "A fase da competição não pode estar vazia")
    private String faseCompeticao;

    @NotBlank(message = "O placar não pode estar vazio")
    private String placar;
}
