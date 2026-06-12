package com.tap.ap2restapi.controllers;

import com.tap.ap2restapi.models.Aluno;
import com.tap.ap2restapi.models.AlunoRepository;
import com.tap.ap2restapi.models.EntidadeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Operation(summary = "Criar um novo Aluno", description = "Cadastra um novo aluno. A matrícula será gerada automaticamente com 8 dígitos.")
    @PostMapping
    public ResponseEntity<Aluno> criarAluno(@Valid @RequestBody com.tap.ap2restapi.dtos.AlunoRequestDTO alunoRequest) {
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
        
        Aluno novoAluno = EntidadeFactory.criarAluno(novaMatricula, alunoRequest.getNome());
        
        return ResponseEntity.ok(alunoRepository.save(novoAluno));
    }

    @Operation(summary = "Listar todos os Alunos", description = "Retorna uma lista contendo todos os alunos cadastrados no sistema.")
    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        return ResponseEntity.ok(alunoRepository.findAll());
    }

    @Operation(summary = "Buscar Aluno por Matrícula", description = "Busca os detalhes de um aluno específico usando sua matrícula.")
    @GetMapping("/{matricula}")
    public ResponseEntity<Aluno> pesquisarPorMatricula(@PathVariable String matricula) {
        return alunoRepository.findById(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar dados do Aluno", description = "Atualiza o nome de um aluno existente através da sua matrícula.")
    @PutMapping("/{matricula}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable String matricula, @Valid @RequestBody com.tap.ap2restapi.dtos.AlunoRequestDTO alunoRequest) {
        return alunoRepository.findById(matricula).map(a -> {
            a.setNome(alunoRequest.getNome());
            return ResponseEntity.ok(alunoRepository.save(a));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir um Aluno", description = "Remove o cadastro de um aluno do sistema permanentemente.")
    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletarAluno(@PathVariable String matricula) {
        if (alunoRepository.existsById(matricula)) {
            alunoRepository.deleteById(matricula);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
