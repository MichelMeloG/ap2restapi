package com.tap.ap2restapi.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, String> {

    @Query("SELECT MAX(a.matricula) FROM Aluno a")
    String findMaxMatricula();
}
