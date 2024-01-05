package com.fatec.tcc.tccaudit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.tcc.tccaudit.models.dto.SummaryDTO;
import com.fatec.tcc.tccaudit.models.entities.Summary;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    @Query("SELECT new com.fatec.tcc.tccaudit.models.dto.SummaryDTO(s.idSummary, s.text, s.topic.idTopic, s.topic.text) FROM Summary s WHERE substring(s.topic.text, 1, 1) like :topic")
    Optional<List<SummaryDTO>> findBySummaryLikeTopic(@Param("topic") String topic);
}