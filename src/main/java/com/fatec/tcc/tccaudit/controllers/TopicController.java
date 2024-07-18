package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.entities.Topic;
import com.fatec.tcc.tccaudit.services.TopicService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping("/{topic}")
    public ResponseEntity<List<Topic>> findByTextStartingWith(@PathVariable("topic") String topic) {
        List<Topic> topics = topicService.findByTextStartingWith(topic);
        return ResponseEntity.ok().body(topics);
    }
}