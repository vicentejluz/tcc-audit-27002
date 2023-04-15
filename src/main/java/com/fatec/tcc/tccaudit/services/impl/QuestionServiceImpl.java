package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.dto.AnswerDTO;
import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.models.utils.QuestionSpecifications;
import com.fatec.tcc.tccaudit.repositories.QuestionRepository;
import com.fatec.tcc.tccaudit.services.QuestionService;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Page<Question> findBySummary(String summary, Pageable pageable) {
        if (summary == null || summary.trim().isEmpty()) {
            throw new IllegalArgumentException("Resumo inválido");
        }
        Specification<Question> spec = QuestionSpecifications.byTopic(summary);
        return questionRepository.findAll(spec, pageable);
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Question updateQuestion(Long id, AnswerDTO answerDTO) {
        try {
            Question entity = questionRepository.getReferenceById(id);
            updateData(entity, answerDTO);
            return questionRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Question entity, AnswerDTO answerDTO) {
        entity.setNaoSeAplica(answerDTO.naoSeAplica());
        entity.setNaoAtende(answerDTO.naoAtende());
        entity.setAtendeParcial(answerDTO.atendeParcial());
        entity.setAtendeTotal(answerDTO.atendeTotal());
    }
}