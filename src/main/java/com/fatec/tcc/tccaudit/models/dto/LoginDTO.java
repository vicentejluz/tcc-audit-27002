package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank String email, @NotBlank String senha)
        implements Serializable {
    private static final long serialVersionUID = 1L;
}