package com.tap.ap2restapi.models;

import java.util.List;

public class EntidadeFactory {

    public static Professor criarProfessor(String matricula, String nome) {
        return Professor.builder()
                .matricula(matricula)
                .nome(nome)
                .build();
    }

    public static Aluno criarAluno(String matricula, String nome) {
        return Aluno.builder()
                .matricula(matricula)
                .nome(nome)
                .build();
    }

    public static Turma criarTurma(String codigo, String nomeDisciplina, Professor professor, List<Aluno> alunos) {
        return Turma.builder()
                .codigo(codigo)
                .nomeDisciplina(nomeDisciplina)
                .professor(professor)
                .alunos(alunos)
                .build();
    }
}
