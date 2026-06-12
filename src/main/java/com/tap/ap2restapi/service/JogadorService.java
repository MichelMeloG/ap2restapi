package com.tap.ap2restapi.service;

import com.tap.ap2restapi.dto.JogadorRequestDTO;
import com.tap.ap2restapi.dto.JogadorResponseDTO;
import com.tap.ap2restapi.dto.SelecaoFactory;
import com.tap.ap2restapi.entity.Jogador;
import com.tap.ap2restapi.entity.Selecao;
import com.tap.ap2restapi.exception.ResourceNotFoundException;
import com.tap.ap2restapi.repository.JogadorRepository;
import com.tap.ap2restapi.repository.SelecaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final SelecaoRepository selecaoRepository;

    public JogadorResponseDTO cadastrarJogador(JogadorRequestDTO dto) {
        Selecao selecao = selecaoRepository.findById(dto.getSelecaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Seleção não encontrada com ID: " + dto.getSelecaoId()));

        Jogador jogador = Jogador.builder()
                .nome(dto.getNome())
                .numeroCamisa(dto.getNumeroCamisa())
                .posicao(dto.getPosicao())
                .idade(dto.getIdade())
                .selecao(selecao)
                .build();

        Jogador salvo = jogadorRepository.save(jogador);
        return SelecaoFactory.converterJogadorParaDTO(salvo);
    }

    public List<JogadorResponseDTO> listarTodos() {
        return jogadorRepository.findAll().stream()
                .map(SelecaoFactory::converterJogadorParaDTO)
                .collect(Collectors.toList());
    }

    public JogadorResponseDTO buscarPorId(Long id) {
        Jogador jogador = jogadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado com ID: " + id));
        return SelecaoFactory.converterJogadorParaDTO(jogador);
    }

    public JogadorResponseDTO atualizarJogador(Long id, JogadorRequestDTO dto) {
        Jogador jogador = jogadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado com ID: " + id));

        Selecao selecao = selecaoRepository.findById(dto.getSelecaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Seleção não encontrada com ID: " + dto.getSelecaoId()));

        jogador.setNome(dto.getNome());
        jogador.setNumeroCamisa(dto.getNumeroCamisa());
        jogador.setPosicao(dto.getPosicao());
        jogador.setIdade(dto.getIdade());
        jogador.setSelecao(selecao);

        Jogador atualizado = jogadorRepository.save(jogador);
        return SelecaoFactory.converterJogadorParaDTO(atualizado);
    }

    public void deletarJogador(Long id) {
        if (!jogadorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Jogador não encontrado com ID: " + id);
        }
        jogadorRepository.deleteById(id);
    }
}
