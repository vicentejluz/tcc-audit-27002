package com.fatec.tcc.tccaudit.services.exceptions;

public class CNPJAlreadyRegisteredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CNPJAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
