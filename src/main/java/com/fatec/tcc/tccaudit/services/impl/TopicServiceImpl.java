package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.entities.Topic;
import com.fatec.tcc.tccaudit.repositories.TopicRepository;
import com.fatec.tcc.tccaudit.services.TopicService;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> findByTextStartingWith(String topic) {
        return topicRepository.findByTextStartingWith(topic).orElseThrow(() -> new ResourceNotFoundException(topic));
    }

}