package com.fatec.tcc.tccaudit.services.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailAlreadyRegisteredException(String msg) {
        super(msg);
    }
}