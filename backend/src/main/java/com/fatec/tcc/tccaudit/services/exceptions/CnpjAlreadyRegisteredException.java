package com.fatec.tcc.tccaudit.services.exceptions;

public class CnpjAlreadyRegisteredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CnpjAlreadyRegisteredException(String msg) {
        super(msg);
    }
}