package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.dto.AnswerDTO;
import com.fatec.tcc.tccaudit.models.entities.Answer;

public interface AnswerService {
    List<Answer> findAll();

    AnswerDTO createOrUpdateAnswer(AnswerDTO answerDTO);

    Answer findById(Long id);
}