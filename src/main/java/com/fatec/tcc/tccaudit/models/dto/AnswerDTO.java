package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record AnswerDTO(int naoSeAplica, int naoAtende, int atendeParcial,
        int atendeTotal) implements Serializable {
    private static final long serialVersionUID = 1L;
}