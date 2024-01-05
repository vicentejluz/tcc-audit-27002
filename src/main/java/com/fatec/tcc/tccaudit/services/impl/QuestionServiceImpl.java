package com.fatec.tcc.tccaudit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.models.entities.Summary;
import com.fatec.tcc.tccaudit.repositories.QuestionRepository;
import com.fatec.tcc.tccaudit.services.QuestionService;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;
import com.fatec.tcc.tccaudit.services.utils.QuestionSpecifications;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Page<Question> findBySummary(Summary summary, Pageable pageable) {
        Specification<Question> specSummary = QuestionSpecifications.bySummary(summary);
        return questionRepository.findAll(specSummary, pageable);
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

}