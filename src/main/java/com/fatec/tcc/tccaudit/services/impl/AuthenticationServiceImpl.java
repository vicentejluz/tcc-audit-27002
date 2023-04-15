package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.dto.JWTtokenDTO;
import com.fatec.tcc.tccaudit.models.dto.LoginDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;
import com.fatec.tcc.tccaudit.security.AuthenticationCentral;
import com.fatec.tcc.tccaudit.security.TokenService;
import com.fatec.tcc.tccaudit.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationCentral authenticationCentral;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public JWTtokenDTO LoginIn(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.email(), loginDTO.senha());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        String token = tokenService.generateToken((Employee) authenticate.getPrincipal());

        return new JWTtokenDTO(token);
    }

    @Override
    public JWTtokenDTO signUp(SignUpDTO singUpDTO) {
        Employee employee = authenticationCentral.cryptography(singUpDTO);
        employeeRepository.save(employee);
        String token = tokenService.generateToken(employee);
        return new JWTtokenDTO(token);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

}