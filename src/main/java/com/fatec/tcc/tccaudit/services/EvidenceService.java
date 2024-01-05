package com.fatec.tcc.tccaudit.services;

import com.fatec.tcc.tccaudit.models.dto.DownloadFileDTO;
import com.fatec.tcc.tccaudit.models.dto.EvidenceDTO;
import com.fatec.tcc.tccaudit.models.dto.SaveOrUploadEvidenceDTO;
import com.fatec.tcc.tccaudit.models.entities.Evidence;

public interface EvidenceService {
    SaveOrUploadEvidenceDTO saveOrUpdateEvidence(Evidence evidence, Long idAnswer);

    DownloadFileDTO downloadFile(Long idEvidence);

    String deleteEvidence(Long idEvidence);

    Evidence existingEvidence(Long id);

    EvidenceDTO getFileName(Long id);
}