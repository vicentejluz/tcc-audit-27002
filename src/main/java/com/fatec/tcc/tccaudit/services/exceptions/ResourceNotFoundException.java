package com.fatec.tcc.tccaudit.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Object id) {
        super("Resource not found. Id " + id);
    }

    public ResourceNotFoundException(String msg, Object obj) {
        super(msg + obj);
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}