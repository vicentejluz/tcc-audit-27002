package com.fatec.tcc.tccaudit.services.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.tcc.tccaudit.models.dto.EvidenceDTO;
import com.fatec.tcc.tccaudit.models.entities.Answer;
import com.fatec.tcc.tccaudit.models.entities.Evidence;
import com.fatec.tcc.tccaudit.repositories.AnswerRepository;
import com.fatec.tcc.tccaudit.repositories.EvidenceRepository;
import com.fatec.tcc.tccaudit.services.AnswerService;
import com.fatec.tcc.tccaudit.services.EvidenceService;
import com.fatec.tcc.tccaudit.services.exceptions.EvidenceUploadException;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class EvidenceServiceImpl implements EvidenceService {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("pdf", "doc", "docx", "xls", "xlsx", "ppt",
            "pptx", "txt");

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    @Transactional
    public EvidenceDTO saveOrUpdateEvidence(Evidence evidence, Long idAnswer) {
        try {
            String message;
            Answer answer = answerService.findById(idAnswer);
            validateMultipartFile(evidence.getMultipartFile());
            byte[] fileBytes = getFileBytes(evidence.getMultipartFile());
            String originalFilename = getOriginalFilename(evidence.getMultipartFile());
            System.out.println(evidence.getIdEvidence());
            if (evidence.getIdEvidence() == null) { // novo objeto Evidence
                createNewEvidence(evidence, answer, fileBytes, originalFilename);
                message = "Successfully created evidence!";
            } else { // objeto Evidence existente
                updateExistingEvidence(evidence, fileBytes, originalFilename);
                message = "Evidence updated successfully!";
            }

            return new EvidenceDTO(evidence.getIdEvidence(), originalFilename, message);
        } catch (IOException e) {
            throw new EvidenceUploadException("Error uploading evidence.");
        }
    }

    @Override
    @Transactional
    public String deleteEvidence(Long idEvidence) {
        try {
            Evidence evidence = evidenceRepository.findById(idEvidence)
                    .orElseThrow(() -> new ResourceNotFoundException("Evidence not found with id " + idEvidence));
            evidenceRepository.delete(evidence);
            return "Evidence with id " + idEvidence + " has been deleted successfully.";
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Evidence not found with id " + idEvidence);
        }
    }

    @Override
    public List<Evidence> findAll() {
        return evidenceRepository.findAll();
    }

    @Override
    public Evidence existingEvidence(Long id) {
        return evidenceRepository.findById(id)
                .orElseGet(() -> {
                    Evidence evidence = new Evidence();
                    return evidence;
                });
    }

    private void validateMultipartFile(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new EvidenceUploadException("Multipart file is null");
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new EvidenceUploadException("Invalid file extension");
        }
    }

    private byte[] getFileBytes(MultipartFile multipartFile) throws IOException {
        byte[] fileBytes = multipartFile.getBytes();
        if (fileBytes == null || fileBytes.length == 0) {
            throw new EvidenceUploadException("File bytes are empty");
        }
        return fileBytes;
    }

    private String getOriginalFilename(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new EvidenceUploadException("Original filename is empty");
        }
        return originalFilename;
    }

    private void createNewEvidence(Evidence evidence, Answer answer, byte[] fileBytes, String originalFilename) {
        evidence.setAnswer(answer);
        evidence.setName(originalFilename);
        evidence.setFile(fileBytes);
        evidenceRepository.save(evidence);

        answer.setEvidence(evidence);
        answerRepository.save(answer);
    }

    private void updateExistingEvidence(Evidence evidence, byte[] fileBytes, String originalFilename) {
        Evidence existingEvidence = evidenceRepository.findById(evidence.getIdEvidence())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Evidence not found with id " + evidence.getIdEvidence()));

        existingEvidence.setName(originalFilename);
        existingEvidence.setFile(fileBytes);
        evidenceRepository.save(existingEvidence);
    }
}