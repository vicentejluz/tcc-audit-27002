package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.dto.LoginAndSignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpCompanyDTO;
import com.fatec.tcc.tccaudit.models.entities.Company;

public interface CompanyService {
    LoginAndSignUpDTO signUpCompany(SignUpCompanyDTO signUpCompanyDTO);

    List<Company> findAll();
}