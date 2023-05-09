package com.fatec.tcc.tccaudit.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Object id) {
        super("Resource not found. Id " + id);
    }

    public ResourceNotFoundException(String message, Object obj) {
        super(message + obj);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}