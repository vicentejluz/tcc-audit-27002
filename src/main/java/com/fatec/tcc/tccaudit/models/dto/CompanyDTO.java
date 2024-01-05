package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record CompanyDTO(String name, String cnpj) implements Serializable {
    private static final long serialVersionUID = 1L;
}