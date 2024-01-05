package com.fatec.tcc.tccaudit.services;

import com.fatec.tcc.tccaudit.models.dto.LoginAndSignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.LoginDTO;

public interface AuthenticationService {
    LoginAndSignUpDTO LoginIn(LoginDTO loginDTO);
}