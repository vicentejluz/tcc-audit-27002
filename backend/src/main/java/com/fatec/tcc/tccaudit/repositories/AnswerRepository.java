package com.fatec.tcc.tccaudit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.tcc.tccaudit.models.entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.question.idQuestion = :idQuestion AND a.company.idCompany = :idCompany")
    Optional<Answer> findByQuestionAndCompany(@Param("idQuestion") Long idQuestion, @Param("idCompany") Long idCompany);
}