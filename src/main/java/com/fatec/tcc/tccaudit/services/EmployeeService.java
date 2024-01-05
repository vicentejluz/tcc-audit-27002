package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.dto.EmployeeDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;

public interface EmployeeService {
    SignUpDTO signUp(SignUpEmployeeDTO signUpEmployeeDTO);

    Employee findById(Long idEmployee);

    String isEnabled(Long idEmployee);

    List<EmployeeDTO> findEmployees();
}