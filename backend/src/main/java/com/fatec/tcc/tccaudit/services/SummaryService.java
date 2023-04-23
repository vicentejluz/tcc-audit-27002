package com.fatec.tcc.tccaudit.services;

import com.fatec.tcc.tccaudit.models.entities.Summary;

public interface SummaryService {
    Summary findById(Long id);
}