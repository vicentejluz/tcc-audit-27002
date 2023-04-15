package com.fatec.tcc.tccaudit.security.exceptions;

public class AuthException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthException(String msg) {
        super(msg);
    }
}