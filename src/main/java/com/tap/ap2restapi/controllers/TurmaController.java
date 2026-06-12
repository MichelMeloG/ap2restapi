package com.tap.ap2restapi.controllers;

import com.tap.ap2restapi.models.Turma;
import com.tap.ap2restapi.models.Professor;
import com.tap.ap2restapi.models.Aluno;
import com.tap.ap2restapi.models.TurmaRepository;
import com.tap.ap2restapi.models.ProfessorRepository;
import com.tap.ap2restapi.models.AlunoRepository;
import com.tap.ap2restapi.models.EntidadeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Operation(summary = "Criar uma nova Turma", description = "Cria uma nova turma no sistema. Não é necessário enviar os alunos neste momento.")
    @PostMapping
    public ResponseEntity<Turma> criarTurma(@Valid @RequestBody com.tap.ap2restapi.dtos.TurmaRequestDTO turmaRequest) {
        Professor professor = null;
        if (turmaRequest.getProfessorMatricula() != null && !turmaRequest.getProfessorMatricula().isEmpty()) {
            professor = professorRepository.findById(turmaRequest.getProfessorMatricula())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        }

        List<Aluno> alunos = new java.util.ArrayList<>();

        Turma novaTurma = EntidadeFactory.criarTurma(turmaRequest.getCodigo(), turmaRequest.getNomeDisciplina(), professor, alunos);
        return ResponseEntity.ok(turmaRepository.save(novaTurma));
    }

    @Operation(summary = "Vincular Aluno à Turma", description = "Matricula um aluno existente em uma turma existente.")
    @PostMapping("/{codigo}/alunos/{matricula}")
    public ResponseEntity<Turma> matricularAluno(@PathVariable String codigo, @PathVariable String matricula) {
        return turmaRepository.findById(codigo).flatMap(turma -> 
            alunoRepository.findById(matricula).map(aluno -> {
                if (!turma.getAlunos().contains(aluno)) {
                    turma.getAlunos().add(aluno);
                    turmaRepository.save(turma);
                }
                return ResponseEntity.ok(turma);
            })
        ).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remover Aluno da Turma", description = "Remove o vínculo de um aluno com a turma.")
    @DeleteMapping("/{codigo}/alunos/{matricula}")
    public ResponseEntity<Turma> removerAluno(@PathVariable String codigo, @PathVariable String matricula) {
        return turmaRepository.findById(codigo).flatMap(turma -> 
            alunoRepository.findById(matricula).map(aluno -> {
                if (turma.getAlunos().contains(aluno)) {
                    turma.getAlunos().remove(aluno);
                    turmaRepository.save(turma);
                }
                return ResponseEntity.ok(turma);
            })
        ).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todas as Turmas", description = "Retorna uma lista com todas as turmas cadastradas.")
    @GetMapping
    public ResponseEntity<List<Turma>> listarTurmas() {
        return ResponseEntity.ok(turmaRepository.findAll());
    }

    @Operation(summary = "Buscar Turma pelo Código", description = "Retorna os dados de uma turma específica baseada no seu código.")
    @GetMapping("/{codigo}")
    public ResponseEntity<Turma> pesquisarPorCodigo(@PathVariable String codigo) {
        return turmaRepository.findById(codigo)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar dados da Turma", description = "Atualiza as informações de uma turma existente.")
    @PutMapping("/{codigo}")
    public ResponseEntity<Turma> atualizarTurma(@PathVariable String codigo, @Valid @RequestBody com.tap.ap2restapi.dtos.TurmaRequestDTO turmaRequest) {
        return turmaRepository.findById(codigo).map(t -> {
            t.setNomeDisciplina(turmaRequest.getNomeDisciplina());
            if (turmaRequest.getProfessorMatricula() != null && !turmaRequest.getProfessorMatricula().isEmpty()) {
                professorRepository.findById(turmaRequest.getProfessorMatricula()).ifPresent(t::setProfessor);
            }
            return ResponseEntity.ok(turmaRepository.save(t));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir uma Turma", description = "Remove permanentemente uma turma do sistema.")
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarTurma(@PathVariable String codigo) {
        if (turmaRepository.existsById(codigo)) {
            turmaRepository.deleteById(codigo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
