package com.fatec.tcc.tccaudit.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.models.entities.Summary;

public interface QuestionService {
    Page<Question> findBySummary(Summary summary, Pageable pageable);

    Question findById(Long id);
}