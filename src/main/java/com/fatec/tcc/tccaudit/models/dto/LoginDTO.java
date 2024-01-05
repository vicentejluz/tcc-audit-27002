package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank @Email String email, @NotBlank String password)
        implements Serializable {
    private static final long serialVersionUID = 1L;
}