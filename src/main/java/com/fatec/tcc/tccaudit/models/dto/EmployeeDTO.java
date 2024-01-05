package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

import com.fatec.tcc.tccaudit.models.entities.EmployeeRole;

public record EmployeeDTO(Long idEmployee, String name, String email, String department, EmployeeRole employeeRole,
        boolean isEnabled)
        implements Serializable {
    private static final long serialVersionUID = 1L;
}
