package com.tap.ap2restapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do jogador não pode estar vazio")
    private String nome;

    @NotNull(message = "O número da camisa não pode ser nulo")
    private Integer numeroCamisa;

    @NotBlank(message = "A posição não pode estar vazia")
    private String posicao;

    @NotNull(message = "A idade não pode ser nula")
    private Integer idade;

    @ManyToOne
    @JoinColumn(name = "selecao_id")
    @JsonIgnoreProperties("jogadores")
    private Selecao selecao;
}
