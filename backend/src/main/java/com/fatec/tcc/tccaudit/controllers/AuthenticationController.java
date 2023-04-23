package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.dto.LoginAndSignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.LoginDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpCompanyDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> employees = authenticationService.findAll();
        return ResponseEntity.ok().body(employees);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginAndSignUpDTO> LoginIn(@Valid @RequestBody LoginDTO loginDTO) {
        LoginAndSignUpDTO login = authenticationService.LoginIn(loginDTO);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/sign-up-company")
    public ResponseEntity<LoginAndSignUpDTO> signUpCompany(@Valid @RequestBody SignUpCompanyDTO signUpCompanyDTO) {
        LoginAndSignUpDTO company = authenticationService.signUpCompany(signUpCompanyDTO);
        return ResponseEntity.ok(company);

    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpDTO> signUp(@Valid @RequestBody SignUpEmployeeDTO signUpEmployeeDTO) {
        SignUpDTO employee = authenticationService.signUp(signUpEmployeeDTO);

        return ResponseEntity.ok().body(employee);
    }
}