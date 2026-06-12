package com.tap.ap2restapi.dto;

import com.tap.ap2restapi.entity.Jogador;
import com.tap.ap2restapi.entity.Partida;
import com.tap.ap2restapi.entity.Selecao;

import java.util.stream.Collectors;

public class SelecaoFactory {

    public static SelecaoResponseDTO converterParaDTO(Selecao selecao) {
        return SelecaoResponseDTO.builder()
                .id(selecao.getId())
                .nomePais(selecao.getNomePais())
                .tecnico(selecao.getTecnico())
                .rankingFifa(selecao.getRankingFifa())
                .quantidadeJogadores(selecao.getJogadores() != null ? selecao.getJogadores().size() : 0)
                .build();
    }

    public static JogadorResponseDTO converterJogadorParaDTO(Jogador jogador) {
        return JogadorResponseDTO.builder()
                .id(jogador.getId())
                .nome(jogador.getNome())
                .numeroCamisa(jogador.getNumeroCamisa())
                .posicao(jogador.getPosicao())
                .idade(jogador.getIdade())
                .nomePaisSelecao(jogador.getSelecao() != null ? jogador.getSelecao().getNomePais() : null)
                .build();
    }

    public static PartidaResponseDTO converterPartidaParaDTO(Partida partida) {
        return PartidaResponseDTO.builder()
                .id(partida.getId())
                .data(partida.getData())
                .estadio(partida.getEstadio())
                .faseCompeticao(partida.getFaseCompeticao())
                .placar(partida.getPlacar())
                .selecoes(partida.getSelecoes() != null ?
                        partida.getSelecoes().stream().map(Selecao::getNomePais).collect(Collectors.toList()) : null)
                .build();
    }
}
