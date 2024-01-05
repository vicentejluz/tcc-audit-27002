package com.fatec.tcc.tccaudit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.tcc.tccaudit.models.entities.Answer;
import com.fatec.tcc.tccaudit.models.entities.Weight;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {
    Optional<Weight> findByAnswer(Answer answer);
}