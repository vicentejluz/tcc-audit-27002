package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.tcc.tccaudit.models.entities.Evidence;
import com.fatec.tcc.tccaudit.services.EvidenceService;

@RestController
@RequestMapping(value = "/upload")
public class EvidenceController {
    @Autowired
    private EvidenceService evidenceService;

    @PostMapping
    public ResponseEntity<Evidence> uploadEvidence(@RequestParam("file") MultipartFile file,
            @RequestParam("idAnswer") Long idAnswer) {
        Evidence evidence = new Evidence();
        evidence.setMultipartFile(file);
        evidenceService.uploadEvidence(evidence, idAnswer);
        return ResponseEntity.ok(evidence);

    }

    @GetMapping
    public ResponseEntity<List<Evidence>> findAll() {
        List<Evidence> evidence = evidenceService.findAll();
        return ResponseEntity.ok().body(evidence);
    }
}