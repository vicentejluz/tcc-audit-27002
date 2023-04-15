package com.fatec.tcc.tccaudit.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fatec.tcc.tccaudit.models.dto.AnswerDTO;
import com.fatec.tcc.tccaudit.models.entities.Question;

public interface QuestionService {
    List<Question> findAll();

    Page<Question> findBySummary(String summary, Pageable pageable);

    Question findById(Long id);

    Question updateQuestion(Long id, AnswerDTO obj);
}