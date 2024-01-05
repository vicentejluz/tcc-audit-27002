package com.fatec.tcc.tccaudit.services.exceptions;

public class InvalidEmployeeNameException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidEmployeeNameException(String msg) {
        super(msg);
    }
}