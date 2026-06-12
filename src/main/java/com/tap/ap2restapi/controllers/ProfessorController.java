package com.tap.ap2restapi.controllers;

import com.tap.ap2restapi.models.Professor;
import com.tap.ap2restapi.models.ProfessorRepository;
import com.tap.ap2restapi.models.EntidadeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping
    public ResponseEntity<Professor> criarProfessor(@Valid @RequestBody Professor professor) {
        Professor novoProfessor = EntidadeFactory.criarProfessor(professor.getMatricula(), professor.getNome());
        return ResponseEntity.ok(professorRepository.save(novoProfessor));
    }

    @GetMapping
    public ResponseEntity<List<Professor>> listarProfessores() {
        return ResponseEntity.ok(professorRepository.findAll());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Professor> pesquisarPorMatricula(@PathVariable String matricula) {
        return professorRepository.findById(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable String matricula, @Valid @RequestBody Professor professor) {
        return professorRepository.findById(matricula).map(p -> {
            p.setNome(professor.getNome());
            return ResponseEntity.ok(professorRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable String matricula) {
        if (professorRepository.existsById(matricula)) {
            professorRepository.deleteById(matricula);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
