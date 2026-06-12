package com.tap.ap2restapi.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, String> {

    @Query("SELECT MAX(p.matricula) FROM Professor p")
    String findMaxMatricula();
}
