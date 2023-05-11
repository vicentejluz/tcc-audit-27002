package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.dto.AnswerDTO;
import com.fatec.tcc.tccaudit.models.dto.AnswerLikeTopicDTO;
import com.fatec.tcc.tccaudit.models.entities.Answer;
import com.fatec.tcc.tccaudit.services.AnswerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/answers")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping
    public ResponseEntity<List<Answer>> findAll() {
        List<Answer> answers = answerService.findAll();
        return ResponseEntity.ok().body(answers);
    }

    @GetMapping("/by-topic")
    public ResponseEntity<List<AnswerLikeTopicDTO>> findByAnswerLikeTopic(
            @RequestParam("idCompany") Long idCompany, @RequestParam("topic") String topic) {
        List<AnswerLikeTopicDTO> answerLikeTopicDTOs = answerService.findByAnswerLikeTopic(idCompany, topic);

        return ResponseEntity.ok().body(answerLikeTopicDTOs);
    }

    @PostMapping
    public ResponseEntity<AnswerDTO> createAnswer(@Valid @RequestBody AnswerDTO answerDTO) {
        AnswerDTO answer = answerService.createOrUpdateAnswer(answerDTO);
        return ResponseEntity.ok(answer);
    }
}