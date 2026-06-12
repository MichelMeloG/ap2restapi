package com.tap.ap2restapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data da partida não pode ser nula")
    private LocalDate data;

    @NotBlank(message = "O estádio não pode estar vazio")
    private String estadio;

    @NotBlank(message = "A fase da competição não pode estar vazia")
    private String faseCompeticao;

    @NotBlank(message = "O placar não pode estar vazio")
    private String placar;

    @ManyToMany
    @JoinTable(
        name = "partida_selecao",
        joinColumns = @JoinColumn(name = "partida_id"),
        inverseJoinColumns = @JoinColumn(name = "selecao_id")
    )
    @JsonIgnoreProperties("partidas")
    @Builder.Default
    private List<Selecao> selecoes = new ArrayList<>();
}
