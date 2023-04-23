package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

import com.fatec.tcc.tccaudit.models.entities.Department;

import jakarta.validation.constraints.NotBlank;

public record SignUpEmployeeDTO(@NotBlank String name, LoginDTO loginDTO,
        Department department) implements Serializable {
    private static final long serialVersionUID = 1L;
}