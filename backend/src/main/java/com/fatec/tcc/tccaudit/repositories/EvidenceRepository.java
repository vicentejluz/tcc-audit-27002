package com.fatec.tcc.tccaudit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.fatec.tcc.tccaudit.models.entities.Evidence;

import jakarta.persistence.LockModeType;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Evidence> findById(Long id);
}