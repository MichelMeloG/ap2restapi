package com.tap.ap2restapi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TurmaRequestDTO {

    @NotBlank(message = "O código não pode estar vazio")
    private String codigo;

    @NotBlank(message = "O nome da disciplina não pode estar vazio")
    private String nomeDisciplina;

    private String professorMatricula;
}
