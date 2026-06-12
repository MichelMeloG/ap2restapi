package com.tap.ap2restapi.service;

import com.tap.ap2restapi.dto.JogadorResponseDTO;
import com.tap.ap2restapi.dto.SelecaoFactory;
import com.tap.ap2restapi.dto.SelecaoRequestDTO;
import com.tap.ap2restapi.dto.SelecaoResponseDTO;
import com.tap.ap2restapi.entity.Selecao;
import com.tap.ap2restapi.exception.ResourceNotFoundException;
import com.tap.ap2restapi.repository.SelecaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SelecaoService {

    private final SelecaoRepository selecaoRepository;

    public SelecaoResponseDTO cadastrarSelecao(SelecaoRequestDTO dto) {
        Selecao selecao = Selecao.builder()
                .nomePais(dto.getNomePais())
                .tecnico(dto.getTecnico())
                .rankingFifa(dto.getRankingFifa())
                .build();
        
        Selecao salva = selecaoRepository.save(selecao);
        return SelecaoFactory.converterParaDTO(salva);
    }

    public List<SelecaoResponseDTO> listarTodasSelecoes() {
        return selecaoRepository.findAll().stream()
                .map(SelecaoFactory::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<JogadorResponseDTO> buscarJogadoresPorSelecao(Long id) {
        Selecao selecao = selecaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seleção não encontrada com ID: " + id));
        
        return selecao.getJogadores().stream()
                .map(SelecaoFactory::converterJogadorParaDTO)
                .collect(Collectors.toList());
    }

    public SelecaoResponseDTO atualizarSelecao(Long id, SelecaoRequestDTO dto) {
        Selecao selecao = selecaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seleção não encontrada com ID: " + id));
        
        selecao.setNomePais(dto.getNomePais());
        selecao.setTecnico(dto.getTecnico());
        selecao.setRankingFifa(dto.getRankingFifa());
        
        Selecao atualizada = selecaoRepository.save(selecao);
        return SelecaoFactory.converterParaDTO(atualizada);
    }

    public void deletarSelecao(Long id) {
        if (!selecaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Seleção não encontrada com ID: " + id);
        }
        selecaoRepository.deleteById(id);
    }
}
