package com.tap.ap2restapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Selecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do país não pode estar vazio")
    private String nomePais;

    @NotBlank(message = "O nome do técnico não pode estar vazio")
    private String tecnico;

    private Integer rankingFifa;

    @OneToMany(mappedBy = "selecao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("selecao")
    @Builder.Default
    private List<Jogador> jogadores = new ArrayList<>();

    @ManyToMany(mappedBy = "selecoes")
    @JsonIgnoreProperties("selecoes")
    @Builder.Default
    private List<Partida> partidas = new ArrayList<>();
}
