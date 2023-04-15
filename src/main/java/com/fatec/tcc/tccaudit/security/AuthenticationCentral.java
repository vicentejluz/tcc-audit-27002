package com.fatec.tcc.tccaudit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;

@Service
public class AuthenticationCentral {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Employee cryptography(SignUpDTO singUpDTO) {
        UserDetails email = employeeRepository.findByEmail(singUpDTO.email());

        if (email != null) {
            throw new RuntimeException("This email is registered! Please, choose another one!");
        }

        String password = passwordEncoder.encode(singUpDTO.senha());
        Employee employee = new Employee(singUpDTO.nome(), singUpDTO.email(), password, singUpDTO.perfil());
        return employee;
    }
}