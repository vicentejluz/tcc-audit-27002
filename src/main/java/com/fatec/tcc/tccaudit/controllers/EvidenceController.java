package com.fatec.tcc.tccaudit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
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

import com.fatec.tcc.tccaudit.models.dto.DownloadFileDTO;
import com.fatec.tcc.tccaudit.models.dto.EvidenceDTO;
import com.fatec.tcc.tccaudit.models.dto.SaveOrUploadEvidenceDTO;
import com.fatec.tcc.tccaudit.models.entities.Evidence;
import com.fatec.tcc.tccaudit.services.EvidenceService;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/evidences")
@SecurityRequirement(name = "bearer-key")
public class EvidenceController {
    @Autowired
    private EvidenceService evidenceService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SaveOrUploadEvidenceDTO> saveOrUpdateEvidence(@RequestPart("file") MultipartFile file,
            @RequestParam("idAnswer") Long idAnswer) {
        Evidence evidence = evidenceService.existingEvidence(idAnswer);
        evidence.setMultipartFile(file);
        SaveOrUploadEvidenceDTO evidenceDTO = evidenceService.saveOrUpdateEvidence(evidence, idAnswer);
        return ResponseEntity.ok(evidenceDTO);
    }

    @GetMapping("/download/{idEvidence}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("idEvidence") Long idEvidence) {
        try {
            DownloadFileDTO downloadFileDTO = evidenceService.downloadFile(idEvidence);

            ByteArrayResource resource = new ByteArrayResource(downloadFileDTO.file(), downloadFileDTO.name());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + downloadFileDTO.name());
            headers.add("Access-Control-Expose-Headers", "Content-Disposition");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(downloadFileDTO.file().length)
                    .body(resource);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{idEvidence}")
    public ResponseEntity<String> deleteEvidence(@PathVariable Long idEvidence) {
        String message = evidenceService.deleteEvidence(idEvidence);
        return ResponseEntity.ok(message);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EvidenceDTO> getFileName(@PathVariable Long id) {
        EvidenceDTO name = evidenceService.getFileName(id);
        return ResponseEntity.ok().body(name);
    }
}