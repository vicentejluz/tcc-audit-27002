package com.fatec.tcc.tccaudit.models.entities;

public enum EmployeeRole {
    ADMIN("ROLE_ADMIN"),
    EMPLOYEE("ROLE_EMPLOYEE");

    private String employeeRole;

    EmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }
}