package com.fatec.tcc.tccaudit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.tcc.tccaudit.models.entities.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<List<Topic>> findByTextStartingWith(String topic);
}