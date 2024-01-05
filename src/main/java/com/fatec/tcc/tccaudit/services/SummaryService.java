package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.dto.SummaryDTO;
import com.fatec.tcc.tccaudit.models.entities.Summary;

public interface SummaryService {
    Summary findById(Long id);

    List<SummaryDTO> findBySummaryLikeTopic(String topic);
}