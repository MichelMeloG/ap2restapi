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

    @PostMapping
    public ResponseEntity<Turma> criarTurma(@Valid @RequestBody Turma turmaRequest) {
        Professor professor = null;
        if (turmaRequest.getProfessor() != null && turmaRequest.getProfessor().getMatricula() != null) {
            professor = professorRepository.findById(turmaRequest.getProfessor().getMatricula())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        }

        List<Aluno> alunos = null;
        if (turmaRequest.getAlunos() != null && !turmaRequest.getAlunos().isEmpty()) {
            List<String> alunoIds = turmaRequest.getAlunos().stream().map(Aluno::getMatricula).toList();
            alunos = alunoRepository.findAllById(alunoIds);
        }

        Turma novaTurma = EntidadeFactory.criarTurma(turmaRequest.getCodigo(), turmaRequest.getNomeDisciplina(), professor, alunos);
        return ResponseEntity.ok(turmaRepository.save(novaTurma));
    }

    @GetMapping
    public ResponseEntity<List<Turma>> listarTurmas() {
        return ResponseEntity.ok(turmaRepository.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Turma> pesquisarPorCodigo(@PathVariable String codigo) {
        return turmaRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Turma> atualizarTurma(@PathVariable String codigo, @Valid @RequestBody Turma turmaRequest) {
        return turmaRepository.findById(codigo).map(t -> {
            t.setNomeDisciplina(turmaRequest.getNomeDisciplina());
            if (turmaRequest.getProfessor() != null && turmaRequest.getProfessor().getMatricula() != null) {
                professorRepository.findById(turmaRequest.getProfessor().getMatricula()).ifPresent(t::setProfessor);
            }
            if (turmaRequest.getAlunos() != null) {
                List<String> alunoIds = turmaRequest.getAlunos().stream().map(Aluno::getMatricula).toList();
                t.setAlunos(alunoRepository.findAllById(alunoIds));
            }
            return ResponseEntity.ok(turmaRepository.save(t));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarTurma(@PathVariable String codigo) {
        if (turmaRepository.existsById(codigo)) {
            turmaRepository.deleteById(codigo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
