package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.tcc.tccaudit.models.dto.EvidenceDTO;
import com.fatec.tcc.tccaudit.models.entities.Evidence;
import com.fatec.tcc.tccaudit.services.EvidenceService;

@RestController
@RequestMapping(value = "/evidences")
public class EvidenceController {
    @Autowired
    private EvidenceService evidenceService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EvidenceDTO> saveOrUpdateEvidence(@RequestPart("file") MultipartFile file,
            @RequestParam("idAnswer") Long idAnswer) {
        Evidence evidence = evidenceService.existingEvidence(idAnswer);
        evidence.setMultipartFile(file);
        EvidenceDTO evidenceDTO = evidenceService.saveOrUpdateEvidence(evidence, idAnswer);
        return ResponseEntity.ok(evidenceDTO);

    }

    @DeleteMapping("/delete/{idEvidence}")
    public ResponseEntity<String> deleteEvidence(@PathVariable Long idEvidence) {
        String message = evidenceService.deleteEvidence(idEvidence);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<Evidence>> findAll() {
        List<Evidence> evidence = evidenceService.findAll();
        return ResponseEntity.ok().body(evidence);
    }
}