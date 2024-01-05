package com.fatec.tcc.tccaudit.security.exceptions;

public class InvalidAuthAndSignUpException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidAuthAndSignUpException(String msg) {
        super(msg);
    }
}