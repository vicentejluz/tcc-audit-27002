package com.fatec.tcc.tccaudit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.models.entities.Summary;
import com.fatec.tcc.tccaudit.services.QuestionService;
import com.fatec.tcc.tccaudit.services.SummaryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/questions")
@SecurityRequirement(name = "bearer-key")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private SummaryService summaryService;

    @GetMapping(value = "/summaries/{idSummary}")
    public ResponseEntity<Page<Question>> findBySummary(@PathVariable Long idSummary,
            Pageable pageable) {
        Summary summary = summaryService.findById(idSummary);
        Page<Question> questions = questionService.findBySummary(summary, pageable);
        return ResponseEntity.ok().body(questions);
    }
}