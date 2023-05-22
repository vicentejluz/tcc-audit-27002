package com.fatec.tcc.tccaudit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.tcc.tccaudit.models.entities.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT t FROM Topic t WHERE substring(t.text, 1, 1) like :topic")
    Optional<List<Topic>> findByText(@Param("topic") String topic);
}