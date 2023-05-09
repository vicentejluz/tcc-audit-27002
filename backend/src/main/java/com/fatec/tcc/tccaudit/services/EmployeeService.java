package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;

public interface EmployeeService {
    String deleteEmployee(Long idEmployee);

    SignUpDTO signUp(SignUpEmployeeDTO signUpEmployeeDTO);

    List<Employee> findAll();

    Employee findById(Long id);
}