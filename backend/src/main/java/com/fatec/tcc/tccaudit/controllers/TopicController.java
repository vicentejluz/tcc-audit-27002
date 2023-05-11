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

@RestController
@RequestMapping(value = "/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<List<Topic>> findAll() {
        List<Topic> topics = topicService.findAll();
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping("/{topic}")
    public ResponseEntity<List<Topic>> findByText(@PathVariable("topic") String topic) {
        List<Topic> topics = topicService.findByText(topic);
        return ResponseEntity.ok().body(topics);
    }
}