package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.dto.AnswerDTO;
import com.fatec.tcc.tccaudit.models.entities.Answer;
import com.fatec.tcc.tccaudit.models.entities.Company;
import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.repositories.AnswerRepository;
import com.fatec.tcc.tccaudit.repositories.CompanyRepository;
import com.fatec.tcc.tccaudit.services.AnswerService;
import com.fatec.tcc.tccaudit.services.QuestionService;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @Transactional
    public AnswerDTO createOrUpdateAnswer(AnswerDTO answerDTO) {
        Optional<Answer> existingAnswer = findExistingAnswer(answerDTO);

        if (existingAnswer.isPresent()) {
            updateAnswer(existingAnswer.get(), answerDTO);
        } else {
            createAnswer(answerDTO);
        }

        return answerDTO;
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    private Optional<Answer> findExistingAnswer(AnswerDTO answerDTO) {
        return answerRepository.findByQuestionAndCompany(answerDTO.idQuestion(), answerDTO.idCompany());
    }

    private void createAnswer(AnswerDTO answerDTO) {
        Question question = findQuestion(answerDTO.idQuestion());
        Company company = findCompany(answerDTO.idCompany());
        Answer answer = new Answer(question, company, answerDTO.notApplicable(), answerDTO.notMet(),
                answerDTO.partiallyMet(), answerDTO.fullyMet());
        answerRepository.save(answer);
    }

    private void updateAnswer(Answer answer, AnswerDTO answerDTO) {
        answer.setNotApplicable(answerDTO.notApplicable());
        answer.setNotMet(answerDTO.notMet());
        answer.setPartiallyMet(answerDTO.partiallyMet());
        answer.setFullyMet(answerDTO.fullyMet());
        answerRepository.save(answer);
    }

    private Question findQuestion(Long questionId) {
        return questionService.findById(questionId);
    }

    private Company findCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(companyId));
    }

    @Override
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

}