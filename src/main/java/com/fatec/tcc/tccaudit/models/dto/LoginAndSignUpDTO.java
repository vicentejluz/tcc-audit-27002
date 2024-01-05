package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record LoginAndSignUpDTO(
        Long id, String name,
        String email, String token) implements Serializable {
    private static final long serialVersionUID = 1L;
}