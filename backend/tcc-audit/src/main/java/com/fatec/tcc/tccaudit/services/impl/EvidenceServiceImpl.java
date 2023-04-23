package com.fatec.tcc.tccaudit.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.tcc.tccaudit.models.entities.Answer;
import com.fatec.tcc.tccaudit.models.entities.Evidence;
import com.fatec.tcc.tccaudit.repositories.AnswerRepository;
import com.fatec.tcc.tccaudit.repositories.EvidenceRepository;
import com.fatec.tcc.tccaudit.services.AnswerService;
import com.fatec.tcc.tccaudit.services.EvidenceService;
import com.fatec.tcc.tccaudit.services.exceptions.EvidenceUploadException;

import jakarta.transaction.Transactional;

@Service
public class EvidenceServiceImpl implements EvidenceService {
    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    @Transactional
    public Evidence uploadEvidence(Evidence evidence, Long idAnswer) {
        try {
            Answer answer = answerService.findById(idAnswer);
            validateMultipartFile(evidence.getMultipartFile());
            byte[] fileBytes = getFileBytes(evidence.getMultipartFile());
            String originalFilename = getOriginalFilename(evidence.getMultipartFile());
            updateEvidence(evidence, answer, fileBytes, originalFilename);
            evidenceRepository.save(evidence);
            answerRepository.save(answer);
            return evidence;
        } catch (IOException e) {
            throw new EvidenceUploadException("Error uploading evidence.");
        }
    }

    @Override
    public List<Evidence> findAll() {
        return evidenceRepository.findAll();
    }

    private void validateMultipartFile(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new IllegalArgumentException("Multipart file is null");
        }
    }

    private byte[] getFileBytes(MultipartFile multipartFile) throws IOException {
        byte[] fileBytes = multipartFile.getBytes();
        if (fileBytes == null || fileBytes.length == 0) {
            throw new IllegalArgumentException("File bytes are empty");
        }
        return fileBytes;
    }

    private String getOriginalFilename(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("Original filename is empty");
        }
        return originalFilename;
    }

    private void updateEvidence(Evidence evidence, Answer answer, byte[] fileBytes, String originalFilename) {
        evidence.setName(originalFilename);
        evidence.setFile(fileBytes);
        evidence.setAnswer(answer);
        answer.setEvidence(evidence);
    }

}