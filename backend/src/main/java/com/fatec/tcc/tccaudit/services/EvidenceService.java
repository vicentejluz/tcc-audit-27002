package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.dto.EvidenceDTO;
import com.fatec.tcc.tccaudit.models.entities.Evidence;

public interface EvidenceService {
    EvidenceDTO saveOrUpdateEvidence(Evidence evidence, Long idAnswer);

    String deleteEvidence(Long idEvidence);

    List<Evidence> findAll();

    Evidence existingEvidence(Long id);
}