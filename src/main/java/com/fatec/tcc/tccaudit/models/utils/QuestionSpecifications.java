package com.fatec.tcc.tccaudit.models.utils;

import org.springframework.data.jpa.domain.Specification;

import com.fatec.tcc.tccaudit.models.entities.Question;

public class QuestionSpecifications {

    public static Specification<Question> byTopic(String summary) {
        return (root, query, cb) -> cb.equal(root.get("resumo"), summary);
    }

}