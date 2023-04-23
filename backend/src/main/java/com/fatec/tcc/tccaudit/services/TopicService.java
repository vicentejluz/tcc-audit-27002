package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.entities.Topic;

public interface TopicService {
    Topic findById(Long id);

    List<Topic> findAll();
}