package com.tap.ap2restapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JogadorResponseDTO {
    private Long id;
    private String nome;
    private Integer numeroCamisa;
    private String posicao;
    private Integer idade;
    private String nomePaisSelecao;
}
