package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record JWTtokenDTO(String token) implements Serializable {
    private static final long serialVersionUID = 1L;
}