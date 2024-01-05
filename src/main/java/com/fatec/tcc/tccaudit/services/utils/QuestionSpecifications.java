package com.fatec.tcc.tccaudit.services.utils;

import org.springframework.data.jpa.domain.Specification;

import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.models.entities.Summary;

import jakarta.persistence.criteria.Join;

public class QuestionSpecifications {

    public static Specification<Question> bySummary(Summary summary) {
        return (root, query, criteriaBuilder) -> {
            Join<Question, Summary> summaryJoin = root.join("summary");
            return criteriaBuilder.equal(summaryJoin.get("idSummary"), summary.getIdSummary());
        };
    }
}