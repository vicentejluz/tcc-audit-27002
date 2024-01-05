package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

public record DownloadFileDTO(String name, byte[] file) implements Serializable {
    private static final long serialVersionUID = 1L;
}