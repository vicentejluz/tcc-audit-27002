package com.fatec.tcc.tccaudit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.fatec.tcc.tccaudit.models.dto.LoginAndSignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.LoginDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.security.TokenService;
import com.fatec.tcc.tccaudit.security.exceptions.InvalidAuthAndSignUpException;
import com.fatec.tcc.tccaudit.services.AuthenticationService;
import com.fatec.tcc.tccaudit.services.exceptions.DatabaseException;
import com.fatec.tcc.tccaudit.services.exceptions.EmployeeAccountBlockedException;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    @Transactional
    public LoginAndSignUpDTO LoginIn(LoginDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.email(), loginDTO.password());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            Employee employee = (Employee) authenticate.getPrincipal();
            String token = tokenService.generateToken(employee);
            return new LoginAndSignUpDTO(employee.getIdEmployee(), employee.getName(), employee.getEmail(), token);

        } catch (DisabledException e) {
            throw new EmployeeAccountBlockedException("Sua conta está bloqueada. Entre em contato com o suporte.");
        } catch (BadCredentialsException e) {
            throw new InvalidAuthAndSignUpException("Email ou senha inválidos!");
        } catch (TransactionSystemException e) {
            throw new DatabaseException("Error while accessing the database" + e.getMessage());
        }
    }
}