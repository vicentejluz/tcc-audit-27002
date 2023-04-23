package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.entities.Evidence;

public interface EvidenceService {
    Evidence uploadEvidence(Evidence evidence, Long idAnswer);

    List<Evidence> findAll();
}