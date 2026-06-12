package com.tap.ap2restapi.controllers;

import com.tap.ap2restapi.models.Professor;
import com.tap.ap2restapi.models.ProfessorRepository;
import com.tap.ap2restapi.models.EntidadeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Operation(summary = "Criar um novo Professor", description = "Cadastra um novo professor no sistema. A matrícula será gerada automaticamente com 5 dígitos.")
    @PostMapping
    public ResponseEntity<Professor> criarProfessor(@Valid @RequestBody com.tap.ap2restapi.dtos.ProfessorRequestDTO professorRequest) {
        String maxMatricula = professorRepository.findMaxMatricula();
        String novaMatricula = "00001";
        if (maxMatricula != null && !maxMatricula.isEmpty()) {
            try {
                long currentMax = Long.parseLong(maxMatricula);
                novaMatricula = String.format("%05d", currentMax + 1);
            } catch (NumberFormatException e) {
                // Keep default if parsing fails
            }
        }

        Professor novoProfessor = EntidadeFactory.criarProfessor(novaMatricula, professorRequest.getNome());
        return ResponseEntity.ok(professorRepository.save(novoProfessor));
    }

    @Operation(summary = "Listar todos os Professores", description = "Retorna uma lista contendo todos os professores cadastrados.")
    @GetMapping
    public ResponseEntity<List<Professor>> listarProfessores() {
        return ResponseEntity.ok(professorRepository.findAll());
    }

    @Operation(summary = "Buscar Professor por Matrícula", description = "Retorna as informações de um professor específico buscando pela sua matrícula.")
    @GetMapping("/{matricula}")
    public ResponseEntity<Professor> pesquisarPorMatricula(@PathVariable String matricula) {
        return professorRepository.findById(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar dados do Professor", description = "Altera as informações (como nome) de um professor já cadastrado.")
    @PutMapping("/{matricula}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable String matricula, @Valid @RequestBody com.tap.ap2restapi.dtos.ProfessorRequestDTO professorRequest) {
        return professorRepository.findById(matricula).map(p -> {
            p.setNome(professorRequest.getNome());
            return ResponseEntity.ok(professorRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir um Professor", description = "Remove um professor do sistema. (Atenção: Não exclua se ele estiver vinculado a uma turma).")
    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable String matricula) {
        if (professorRepository.existsById(matricula)) {
            professorRepository.deleteById(matricula);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
