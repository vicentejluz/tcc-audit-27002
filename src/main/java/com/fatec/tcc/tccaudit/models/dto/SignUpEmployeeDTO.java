package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public record SignUpEmployeeDTO(@NotBlank String name, LoginDTO loginDTO,
        Long idDepartment) implements Serializable {
    private static final long serialVersionUID = 1L;
}