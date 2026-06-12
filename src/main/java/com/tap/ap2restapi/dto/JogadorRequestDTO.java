package com.tap.ap2restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JogadorRequestDTO {
    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;

    @NotNull(message = "O número da camisa não pode ser nulo")
    private Integer numeroCamisa;

    @NotBlank(message = "A posição não pode estar vazia")
    private String posicao;

    @NotNull(message = "A idade não pode ser nula")
    private Integer idade;

    @NotNull(message = "O ID da seleção não pode ser nulo")
    private Long selecaoId;
}
