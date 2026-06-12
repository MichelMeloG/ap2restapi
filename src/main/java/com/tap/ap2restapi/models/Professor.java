package com.tap.ap2restapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Professor {

    @Id
    @NotBlank(message = "A matrícula não pode estar vazia")
    @Size(min = 5, max = 5, message = "A matrícula deve ter exatos 5 caracteres")
    private String matricula;
    private String nome;
}
