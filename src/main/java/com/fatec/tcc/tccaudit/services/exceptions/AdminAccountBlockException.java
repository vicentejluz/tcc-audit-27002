package com.fatec.tcc.tccaudit.services.exceptions;

public class AdminAccountBlockException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AdminAccountBlockException(String msg) {
        super(msg);
    }
}