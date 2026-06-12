package com.tap.ap2restapi.service;

import com.tap.ap2restapi.dto.PartidaRequestDTO;
import com.tap.ap2restapi.dto.PartidaResponseDTO;
import com.tap.ap2restapi.dto.SelecaoFactory;
import com.tap.ap2restapi.entity.Partida;
import com.tap.ap2restapi.entity.Selecao;
import com.tap.ap2restapi.exception.ResourceNotFoundException;
import com.tap.ap2restapi.repository.PartidaRepository;
import com.tap.ap2restapi.repository.SelecaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final SelecaoRepository selecaoRepository;

    public PartidaResponseDTO cadastrarPartida(PartidaRequestDTO dto) {
        Partida partida = Partida.builder()
                .data(dto.getData())
                .estadio(dto.getEstadio())
                .faseCompeticao(dto.getFaseCompeticao())
                .placar(dto.getPlacar())
                .build();

        Partida salva = partidaRepository.save(partida);
        return SelecaoFactory.converterPartidaParaDTO(salva);
    }

    public PartidaResponseDTO vincularSelecaoNaPartida(Long partidaId, Long selecaoId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada com ID: " + partidaId));

        Selecao selecao = selecaoRepository.findById(selecaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Seleção não encontrada com ID: " + selecaoId));

        if (!partida.getSelecoes().contains(selecao)) {
            partida.getSelecoes().add(selecao);
            partidaRepository.save(partida);
        }

        return SelecaoFactory.converterPartidaParaDTO(partida);
    }

    public List<PartidaResponseDTO> listarPartidas() {
        return partidaRepository.findAll().stream()
                .map(SelecaoFactory::converterPartidaParaDTO)
                .collect(Collectors.toList());
    }

    public PartidaResponseDTO buscarPorId(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada com ID: " + id));
        return SelecaoFactory.converterPartidaParaDTO(partida);
    }

    public PartidaResponseDTO atualizarPartida(Long id, PartidaRequestDTO dto) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada com ID: " + id));

        partida.setData(dto.getData());
        partida.setEstadio(dto.getEstadio());
        partida.setFaseCompeticao(dto.getFaseCompeticao());
        partida.setPlacar(dto.getPlacar());

        Partida atualizada = partidaRepository.save(partida);
        return SelecaoFactory.converterPartidaParaDTO(atualizada);
    }

    public void deletarPartida(Long id) {
        if (!partidaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Partida não encontrada com ID: " + id);
        }
        partidaRepository.deleteById(id);
    }

    public PartidaResponseDTO removerSelecaoDaPartida(Long partidaId, Long selecaoId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada com ID: " + partidaId));

        Selecao selecao = selecaoRepository.findById(selecaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Seleção não encontrada com ID: " + selecaoId));

        if (partida.getSelecoes().contains(selecao)) {
            partida.getSelecoes().remove(selecao);
            partidaRepository.save(partida);
        }

        return SelecaoFactory.converterPartidaParaDTO(partida);
    }
}
