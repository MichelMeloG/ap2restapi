package com.tap.ap2restapi.controllers;

import com.tap.ap2restapi.models.Aluno;
import com.tap.ap2restapi.models.AlunoRepository;
import com.tap.ap2restapi.models.EntidadeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping
    public ResponseEntity<Aluno> criarAluno(@Valid @RequestBody Aluno aluno) {
        String maxMatricula = alunoRepository.findMaxMatricula();
        String novaMatricula = "00000001";
        if (maxMatricula != null && !maxMatricula.isEmpty()) {
            try {
                long currentMax = Long.parseLong(maxMatricula);
                novaMatricula = String.format("%08d", currentMax + 1);
            } catch (NumberFormatException e) {
                // Keep default if parsing fails
            }
        }
        
        Aluno novoAluno = EntidadeFactory.criarAluno(novaMatricula, aluno.getNome());
        
        return ResponseEntity.ok(alunoRepository.save(novoAluno));
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        return ResponseEntity.ok(alunoRepository.findAll());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Aluno> pesquisarPorMatricula(@PathVariable String matricula) {
        return alunoRepository.findById(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable String matricula, @Valid @RequestBody Aluno aluno) {
        return alunoRepository.findById(matricula).map(a -> {
            a.setNome(aluno.getNome());
            return ResponseEntity.ok(alunoRepository.save(a));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletarAluno(@PathVariable String matricula) {
        if (alunoRepository.existsById(matricula)) {
            alunoRepository.deleteById(matricula);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
