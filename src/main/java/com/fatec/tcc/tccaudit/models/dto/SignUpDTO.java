package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record SignUpDTO(
        Long id, String name, String email, String department, String company, String cnpj, String street, String city,
        String state,
        String postalCode) implements Serializable {
    private static final long serialVersionUID = 1L;
}