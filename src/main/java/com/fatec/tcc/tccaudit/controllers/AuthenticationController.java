package com.fatec.tcc.tccaudit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.dto.LoginAndSignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.LoginDTO;

import com.fatec.tcc.tccaudit.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginAndSignUpDTO> LoginIn(@Valid @RequestBody LoginDTO loginDTO) {
        LoginAndSignUpDTO login = authenticationService.LoginIn(loginDTO);
        return ResponseEntity.ok(login);
    }

}