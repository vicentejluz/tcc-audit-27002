package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.dto.SummaryDTO;
import com.fatec.tcc.tccaudit.services.SummaryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/summaries")
@SecurityRequirement(name = "bearer-key")
public class SummaryController {
    @Autowired
    private SummaryService summaryService;

    @GetMapping("/{topic}")
    public ResponseEntity<List<SummaryDTO>> findBySummaryLikeTopic(@PathVariable("topic") String topic) {
        List<SummaryDTO> summaryDTOs = summaryService.findBySummaryLikeTopic(topic);
        return ResponseEntity.ok().body(summaryDTOs);
    }
}