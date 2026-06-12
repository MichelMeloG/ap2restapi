package com.tap.ap2restapi.controller;

import com.tap.ap2restapi.dto.JogadorRequestDTO;
import com.tap.ap2restapi.dto.JogadorResponseDTO;
import com.tap.ap2restapi.service.JogadorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jogadores")
@RequiredArgsConstructor
public class JogadorController {

    private final JogadorService jogadorService;

    @Operation(summary = "Cadastrar um novo Jogador vinculado a uma Seleção")
    @PostMapping
    public ResponseEntity<JogadorResponseDTO> cadastrarJogador(@Valid @RequestBody JogadorRequestDTO request) {
        JogadorResponseDTO response = jogadorService.cadastrarJogador(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos os Jogadores")
    @GetMapping
    public ResponseEntity<java.util.List<JogadorResponseDTO>> listarJogadores() {
        return ResponseEntity.ok(jogadorService.listarTodos());
    }

    @Operation(summary = "Buscar um Jogador por ID")
    @GetMapping("/{id}")
    public ResponseEntity<JogadorResponseDTO> buscarJogadorPorId(@PathVariable Long id) {
        return ResponseEntity.ok(jogadorService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar dados de um Jogador")
    @PutMapping("/{id}")
    public ResponseEntity<JogadorResponseDTO> atualizarJogador(@PathVariable Long id, @Valid @RequestBody JogadorRequestDTO request) {
        return ResponseEntity.ok(jogadorService.atualizarJogador(id, request));
    }

    @Operation(summary = "Deletar um Jogador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogador(@PathVariable Long id) {
        jogadorService.deletarJogador(id);
        return ResponseEntity.noContent().build();
    }
}
