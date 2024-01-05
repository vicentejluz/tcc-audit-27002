package com.fatec.tcc.tccaudit.services.exceptions;

public class EmployeeAccountBlockedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmployeeAccountBlockedException(String msg) {
        super(msg);
    }
}