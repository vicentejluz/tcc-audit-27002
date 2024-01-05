package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.dto.SummaryDTO;
import com.fatec.tcc.tccaudit.models.entities.Summary;
import com.fatec.tcc.tccaudit.repositories.SummaryRepository;
import com.fatec.tcc.tccaudit.services.SummaryService;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

@Service
public class SummaryServiceImpl implements SummaryService {
    @Autowired
    private SummaryRepository summaryRepository;

    @Override
    public Summary findById(Long id) {
        return summaryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<SummaryDTO> findBySummaryLikeTopic(String topic) {
        return summaryRepository.findBySummaryLikeTopic(topic).orElseThrow(() -> new ResourceNotFoundException(topic));
    }

}