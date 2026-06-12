package com.tap.ap2restapi.controller;

import com.tap.ap2restapi.dto.JogadorResponseDTO;
import com.tap.ap2restapi.dto.SelecaoRequestDTO;
import com.tap.ap2restapi.dto.SelecaoResponseDTO;
import com.tap.ap2restapi.service.SelecaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/selecoes")
@RequiredArgsConstructor
public class SelecaoController {

    private final SelecaoService selecaoService;

    @Operation(summary = "Cadastrar uma nova Seleção")
    @PostMapping
    public ResponseEntity<SelecaoResponseDTO> cadastrarSelecao(@Valid @RequestBody SelecaoRequestDTO request) {
        SelecaoResponseDTO response = selecaoService.cadastrarSelecao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todas as Seleções")
    @GetMapping
    public ResponseEntity<List<SelecaoResponseDTO>> listarSelecoes() {
        List<SelecaoResponseDTO> response = selecaoService.listarTodasSelecoes();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar Jogadores de uma Seleção")
    @GetMapping("/{id}/jogadores")
    public ResponseEntity<List<JogadorResponseDTO>> buscarJogadoresPorSelecao(@PathVariable Long id) {
        List<JogadorResponseDTO> response = selecaoService.buscarJogadoresPorSelecao(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar dados de uma Seleção")
    @PutMapping("/{id}")
    public ResponseEntity<SelecaoResponseDTO> atualizarSelecao(@PathVariable Long id, @Valid @RequestBody SelecaoRequestDTO request) {
        SelecaoResponseDTO response = selecaoService.atualizarSelecao(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar uma Seleção")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSelecao(@PathVariable Long id) {
        selecaoService.deletarSelecao(id);
        return ResponseEntity.noContent().build();
    }
}
