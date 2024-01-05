package com.fatec.tcc.tccaudit.models.dto;

import java.io.Serializable;

import com.fatec.tcc.tccaudit.models.entities.Address;

public record SignUpCompanyDTO(Address address, CompanyDTO companyDTO, LoginDTO loginDTO)
        implements Serializable {
    private static final long serialVersionUID = 1L;
}