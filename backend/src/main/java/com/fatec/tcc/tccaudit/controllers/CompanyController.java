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
import com.fatec.tcc.tccaudit.models.dto.SignUpCompanyDTO;
import com.fatec.tcc.tccaudit.models.entities.Company;
import com.fatec.tcc.tccaudit.services.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> findAll() {
        List<Company> companies = companyService.findAll();
        return ResponseEntity.ok().body(companies);
    }

    @PostMapping("/sign-up-company")
    public ResponseEntity<LoginAndSignUpDTO> signUpCompany(@Valid @RequestBody SignUpCompanyDTO signUpCompanyDTO) {
        LoginAndSignUpDTO company = companyService.signUpCompany(signUpCompanyDTO);
        return ResponseEntity.ok(company);
    }
}