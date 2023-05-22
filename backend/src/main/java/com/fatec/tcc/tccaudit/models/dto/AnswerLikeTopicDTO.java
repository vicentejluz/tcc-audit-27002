package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record AnswerLikeTopicDTO(boolean notApplicable,
        boolean notMet,
        boolean partiallyMet,
        boolean fullyMet,
        Long idCompany,
        Long idQuestion,
        String text) implements Serializable {
    private static final long serialVersionUID = 1L;
}