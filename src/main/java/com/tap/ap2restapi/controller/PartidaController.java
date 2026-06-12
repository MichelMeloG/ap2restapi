package com.tap.ap2restapi.controller;

import com.tap.ap2restapi.dto.PartidaRequestDTO;
import com.tap.ap2restapi.dto.PartidaResponseDTO;
import com.tap.ap2restapi.service.PartidaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
@RequiredArgsConstructor
public class PartidaController {

    private final PartidaService partidaService;

    @Operation(summary = "Cadastrar uma nova Partida")
    @PostMapping
    public ResponseEntity<PartidaResponseDTO> cadastrarPartida(@Valid @RequestBody PartidaRequestDTO request) {
        PartidaResponseDTO response = partidaService.cadastrarPartida(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Vincular uma Seleção a uma Partida")
    @PostMapping("/{id}/selecoes/{idSelecao}")
    public ResponseEntity<PartidaResponseDTO> vincularSelecao(@PathVariable Long id, @PathVariable Long idSelecao) {
        PartidaResponseDTO response = partidaService.vincularSelecaoNaPartida(id, idSelecao);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar todas as Partidas")
    @GetMapping
    public ResponseEntity<List<PartidaResponseDTO>> listarPartidas() {
        List<PartidaResponseDTO> response = partidaService.listarPartidas();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar uma Partida por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> buscarPartidaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar dados de uma Partida")
    @PutMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> atualizarPartida(@PathVariable Long id, @Valid @RequestBody PartidaRequestDTO request) {
        return ResponseEntity.ok(partidaService.atualizarPartida(id, request));
    }

    @Operation(summary = "Deletar uma Partida")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPartida(@PathVariable Long id) {
        partidaService.deletarPartida(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover uma Seleção de uma Partida")
    @DeleteMapping("/{id}/selecoes/{idSelecao}")
    public ResponseEntity<PartidaResponseDTO> removerSelecao(@PathVariable Long id, @PathVariable Long idSelecao) {
        return ResponseEntity.ok(partidaService.removerSelecaoDaPartida(id, idSelecao));
    }
}
