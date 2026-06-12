package com.tap.ap2restapi.repository;

import com.tap.ap2restapi.entity.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    List<Jogador> findBySelecaoId(Long selecaoId);
}
