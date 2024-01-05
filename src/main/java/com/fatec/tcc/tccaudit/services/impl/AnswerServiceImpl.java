package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.dto.AnswerDTO;
import com.fatec.tcc.tccaudit.models.dto.AnswerLikeTopicDTO;
import com.fatec.tcc.tccaudit.models.entities.Answer;
import com.fatec.tcc.tccaudit.models.entities.Company;
import com.fatec.tcc.tccaudit.models.entities.Question;
import com.fatec.tcc.tccaudit.models.entities.Weight;
import com.fatec.tcc.tccaudit.repositories.AnswerRepository;
import com.fatec.tcc.tccaudit.repositories.CompanyRepository;
import com.fatec.tcc.tccaudit.repositories.WeightRepository;
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

    @Autowired
    private WeightRepository weightRepository;

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
    public List<AnswerLikeTopicDTO> findByAnswerLikeTopic(Long idCompany, String topic) {
        Optional<Company> optionalCompany = companyRepository.findById(idCompany);
        if (optionalCompany.isEmpty()) {
            throw new ResourceNotFoundException(idCompany);
        }
        return answerRepository.findByAnswerLikeTopic(idCompany, topic);
    }

    @Override
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private Optional<Answer> findExistingAnswer(AnswerDTO answerDTO) {
        return answerRepository.findByQuestionIdQuestionAndCompanyIdCompany(answerDTO.idQuestion(),
                answerDTO.idCompany());
    }

    private void createAnswer(AnswerDTO answerDTO) {
        Question question = findQuestion(answerDTO.idQuestion());
        Company company = findCompany(answerDTO.idCompany());
        Answer answer = new Answer(question, company, answerDTO.notApplicable(), answerDTO.notMet(),
                answerDTO.partiallyMet(), answerDTO.fullyMet());
        answerRepository.save(answer);
        setWeight(answerDTO, answer);
    }

    private void updateAnswer(Answer answer, AnswerDTO answerDTO) {
        answer.setNotApplicable(answerDTO.notApplicable());
        answer.setNotMet(answerDTO.notMet());
        answer.setPartiallyMet(answerDTO.partiallyMet());
        answer.setFullyMet(answerDTO.fullyMet());
        answerRepository.save(answer);
        setWeight(answerDTO, answer);
    }

    private Question findQuestion(Long questionId) {
        return questionService.findById(questionId);
    }

    private Company findCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(companyId));
    }

    private void setWeight(AnswerDTO answerDTO, Answer answer) {
        Optional<Weight> existingWeight = weightRepository.findByAnswer(answer);
        if (existingWeight.isPresent()) {
            updateWeight(existingWeight.get(), answerDTO);
        } else {
            createWeight(answerDTO, answer);
        }
    }

    private void createWeight(AnswerDTO answerDTO, Answer answer) {
        Weight weight = new Weight();
        weight.setAnswer(answer);
        setWeightValue(weight, answerDTO);
        weightRepository.save(weight);
    }

    private void updateWeight(Weight weight, AnswerDTO answerDTO) {
        setWeightValue(weight, answerDTO);
        weightRepository.save(weight);
    }

    private void setWeightValue(Weight weight, AnswerDTO answerDTO) {
        if (answerDTO.notApplicable() || answerDTO.notMet()) {
            weight.setWeight(0d);
        } else if (answerDTO.partiallyMet()) {
            weight.setWeight(0.5d);
        } else if (answerDTO.fullyMet()) {
            weight.setWeight(1d);
        }
    }

    @Override
    public Long countByCompanyIdCompany(Long idCompany) {

        Long answerCount = answerRepository.countByCompanyIdCompany(idCompany);

        return answerCount;
    }
}