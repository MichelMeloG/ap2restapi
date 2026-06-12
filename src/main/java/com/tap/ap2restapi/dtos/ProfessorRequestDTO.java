package com.tap.ap2restapi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfessorRequestDTO {
    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;
}
