package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record SummaryDTO(Long idSummary, String text, Long idTopic, String topic) implements Serializable {
    private static final long serialVersionUID = 1L;
}