package com.tap.ap2restapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelecaoResponseDTO {
    private Long id;
    private String nomePais;
    private String tecnico;
    private Integer rankingFifa;
    private Integer quantidadeJogadores;
}
