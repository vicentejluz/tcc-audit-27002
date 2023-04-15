package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.dto.JWTtokenDTO;
import com.fatec.tcc.tccaudit.models.dto.LoginDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;

public interface AuthenticationService {
    JWTtokenDTO LoginIn(LoginDTO loginDTO);

    JWTtokenDTO signUp(SignUpDTO singUpDTO);

    List<Employee> findAll();
}