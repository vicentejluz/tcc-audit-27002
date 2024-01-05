package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record AnswerLikeTopicDTO(
        Long idAnswer,
        boolean notApplicable,
        boolean notMet,
        boolean partiallyMet,
        boolean fullyMet,
        Long idQuestion) implements Serializable {
    private static final long serialVersionUID = 1L;
}