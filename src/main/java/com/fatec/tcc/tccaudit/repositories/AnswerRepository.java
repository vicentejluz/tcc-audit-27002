package com.fatec.tcc.tccaudit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.tcc.tccaudit.models.dto.AnswerLikeTopicDTO;
import com.fatec.tcc.tccaudit.models.entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQuestionIdQuestionAndCompanyIdCompany(Long idQuestion, Long idCompany);

    @Query("SELECT new com.fatec.tcc.tccaudit.models.dto.AnswerLikeTopicDTO(a.idAnswer, a.notApplicable, a.notMet, a.partiallyMet, a.fullyMet, a.question.idQuestion) FROM Answer a WHERE a.company.idCompany = :idCompany AND substring(a.question.summary.topic.text, 1, 1) like :topic")
    List<AnswerLikeTopicDTO> findByAnswerLikeTopic(@Param("idCompany") Long idCompany,
            @Param("topic") String topic);

    Long countByCompanyIdCompany(Long idCompany);
}