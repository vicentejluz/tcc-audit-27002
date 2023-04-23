package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.dto.LoginAndSignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.LoginDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpCompanyDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;

public interface AuthenticationService {
    LoginAndSignUpDTO LoginIn(LoginDTO loginDTO);

    LoginAndSignUpDTO signUpCompany(SignUpCompanyDTO signUpCompanyDTO);

    SignUpDTO signUp(SignUpEmployeeDTO signUpEmployeeDTO);

    List<Employee> findAll();
}