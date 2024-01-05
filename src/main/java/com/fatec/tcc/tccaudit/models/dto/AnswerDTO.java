package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record AnswerDTO(Long idQuestion, Long idCompany, boolean notApplicable, boolean notMet,
        boolean partiallyMet,
        boolean fullyMet) implements Serializable {
    private static final long serialVersionUID = 1L;
}