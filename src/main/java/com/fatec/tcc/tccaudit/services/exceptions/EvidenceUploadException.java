package com.fatec.tcc.tccaudit.services.exceptions;

public class EvidenceUploadException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EvidenceUploadException(String msg) {
        super(msg);
    }
}