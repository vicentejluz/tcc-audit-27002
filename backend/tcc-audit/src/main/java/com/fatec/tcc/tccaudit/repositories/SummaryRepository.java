package com.fatec.tcc.tccaudit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.tcc.tccaudit.models.entities.Summary;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {

}