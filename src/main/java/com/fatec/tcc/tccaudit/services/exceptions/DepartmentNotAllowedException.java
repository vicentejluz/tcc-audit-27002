package com.fatec.tcc.tccaudit.services.exceptions;

public class DepartmentNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DepartmentNotAllowedException(String msg) {
        super(msg);
    }
}