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
    public Topic findById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

}