package com.fatec.tcc.tccaudit.services.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.tcc.tccaudit.models.dto.DownloadFileDTO;
import com.fatec.tcc.tccaudit.models.dto.EvidenceDTO;
import com.fatec.tcc.tccaudit.models.dto.SaveOrUploadEvidenceDTO;
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
            "pptx", "txt", "csv", "jpg", "png");

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    @Transactional
    public SaveOrUploadEvidenceDTO saveOrUpdateEvidence(Evidence evidence, Long idAnswer) {
        try {
            String message;
            Answer answer = answerService.findById(idAnswer);
            validateMultipartFile(evidence.getMultipartFile());
            byte[] fileBytes = getFileBytes(evidence.getMultipartFile());
            String originalFilename = getOriginalFilename(evidence.getMultipartFile());

            if (evidence.getIdEvidence() == null) { // novo objeto Evidence
                Evidence createNewEvidence = createNewEvidence(evidence, answer, fileBytes, originalFilename);
                evidenceRepository.save(createNewEvidence);
                answer.setEvidence(createNewEvidence);
                answerRepository.save(answer);
                message = "Successfully created evidence!";
            } else { // objeto Evidence existente
                Evidence existingEvidence = updateExistingEvidence(evidence, fileBytes, originalFilename);
                evidenceRepository.save(existingEvidence);
                answer.setEvidence(existingEvidence);
                answerRepository.save(answer);
                message = "Evidence updated successfully!";
            }

            return new SaveOrUploadEvidenceDTO(evidence.getIdEvidence(), originalFilename, message);
        } catch (IOException e) {
            throw new EvidenceUploadException("Error uploading evidence.");
        }
    }

    @Override
    public DownloadFileDTO downloadFile(Long idEvidence) {
        Evidence evidence = evidenceRepository.findById(idEvidence)
                .orElseThrow(() -> new ResourceNotFoundException("Evidence not found with id " + idEvidence));

        byte[] fileBytes = evidence.getFile();
        String fileName = evidence.getName();

        DownloadFileDTO downloadFileDTO = new DownloadFileDTO(fileName, fileBytes);

        return downloadFileDTO;
    }

    @Override
    @Transactional
    public String deleteEvidence(Long idEvidence) {
        Evidence evidence = evidenceRepository.findById(idEvidence)
                .orElseThrow(() -> new ResourceNotFoundException("Evidence not found with id " + idEvidence));

        Answer answer = answerService.findById(idEvidence);
        answer.setEvidence(null);
        answerRepository.save(answer);
        evidenceRepository.delete(evidence);

        String message = "Evidence with id " + idEvidence + " has been deleted successfully.";
        return message;
    }

    @Override
    public Evidence existingEvidence(Long id) {
        return evidenceRepository.findById(id)
                .orElseGet(() -> {
                    Evidence evidence = new Evidence();
                    return evidence;
                });
    }

    @Override
    public EvidenceDTO getFileName(Long id) {
        Evidence evidence = evidenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new EvidenceDTO(evidence.getIdEvidence(), evidence.getName());
    }

    private void validateMultipartFile(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new EvidenceUploadException("Multipart file is null");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new EvidenceUploadException("OriginalFilename is null");
        }

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

    private Evidence createNewEvidence(Evidence evidence, Answer answer, byte[] fileBytes, String originalFilename) {
        evidence.setAnswer(answer);
        evidence.setName(originalFilename);
        evidence.setFile(fileBytes);

        return evidence;
    }

    private Evidence updateExistingEvidence(Evidence evidence, byte[] fileBytes, String originalFilename) {
        Evidence existingEvidence = evidenceRepository.findById(evidence.getIdEvidence())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Evidence not found with id " + evidence.getIdEvidence()));

        existingEvidence.setName(originalFilename);
        existingEvidence.setFile(fileBytes);

        return existingEvidence;
    }
}