package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record EvidenceDTO(Long idEvidence, String name) implements Serializable {
    private static final long serialVersionUID = 1L;

}