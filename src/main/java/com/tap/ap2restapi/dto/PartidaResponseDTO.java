package com.tap.ap2restapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PartidaResponseDTO {
    private Long id;
    private LocalDate data;
    private String estadio;
    private String faseCompeticao;
    private String placar;
    private List<String> selecoes;
}
