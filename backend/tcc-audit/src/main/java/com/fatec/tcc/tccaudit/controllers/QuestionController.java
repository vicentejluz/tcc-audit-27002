package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.fatec.tcc.tccaudit.models.dto.AnswerDTO;
import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.models.entities.Summary;
import com.fatec.tcc.tccaudit.services.QuestionService;
import com.fatec.tcc.tccaudit.services.SummaryService;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private SummaryService summaryService;

    @GetMapping
    public ResponseEntity<List<Question>> findAll() {
        List<Question> questions = questionService.findAll();
        return ResponseEntity.ok().body(questions);
    }

    @GetMapping(value = "/summaries/{idSummary}")
    public ResponseEntity<Page<Question>> findByTopicAndSummary(@PathVariable Long idSummary,
            Pageable pageable) {
        Summary summary = summaryService.findById(idSummary);
        Page<Question> questions = questionService.findBySummary(summary, pageable);
        return ResponseEntity.ok().body(questions);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Question> findById(@PathVariable Long id) {
        Question question = questionService.findById(id);
        return ResponseEntity.ok().body(question);
    }

    // @PutMapping(value = "/{id}")
    // public ResponseEntity<Question> updateQuestion(@PathVariable Long id,
    // @RequestBody AnswerDTO answerDTO) {
    // Question question = questionService.updateQuestion(id, answerDTO);
    // return ResponseEntity.ok().body(question);
    // }
}