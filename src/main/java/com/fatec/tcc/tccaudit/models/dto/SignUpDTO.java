package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public record SignUpDTO(@NotBlank String nome, @NotBlank String email, @NotBlank String senha, @NotBlank String perfil)
        implements Serializable {
    private static final long serialVersionUID = 1L;
}