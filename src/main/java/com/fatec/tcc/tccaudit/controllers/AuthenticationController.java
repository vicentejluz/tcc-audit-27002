package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.dto.JWTtokenDTO;
import com.fatec.tcc.tccaudit.models.dto.LoginDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.services.AuthenticationService;

import jakarta.transaction.Transactional;

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
    @Transactional
    public ResponseEntity<JWTtokenDTO> LoginIn(@RequestBody LoginDTO loginDTO) {
        JWTtokenDTO tokenDTO = authenticationService.LoginIn(loginDTO);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/sign-up")
    @Transactional
    public ResponseEntity<JWTtokenDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        JWTtokenDTO tokenDTO = authenticationService.signUp(signUpDTO);
        return ResponseEntity.ok(tokenDTO);
    }
}