package com.fatec.tcc.tccaudit.services;

import com.fatec.tcc.tccaudit.models.dto.LoginAndSignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpCompanyDTO;

public interface CompanyService {
    LoginAndSignUpDTO signUpCompany(SignUpCompanyDTO signUpCompanyDTO);
}