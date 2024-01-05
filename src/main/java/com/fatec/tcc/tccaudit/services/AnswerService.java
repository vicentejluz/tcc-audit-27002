package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.dto.AnswerDTO;
import com.fatec.tcc.tccaudit.models.dto.AnswerLikeTopicDTO;
import com.fatec.tcc.tccaudit.models.entities.Answer;

public interface AnswerService {
    List<AnswerLikeTopicDTO> findByAnswerLikeTopic(Long idCompany, String topic);

    AnswerDTO createOrUpdateAnswer(AnswerDTO answerDTO);

    Answer findById(Long id);

    Long countByCompanyIdCompany(Long idCompany);
}