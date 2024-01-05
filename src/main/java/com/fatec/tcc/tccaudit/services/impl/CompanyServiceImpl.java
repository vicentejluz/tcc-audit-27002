package com.fatec.tcc.tccaudit.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.fatec.tcc.tccaudit.models.dto.LoginAndSignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpCompanyDTO;
import com.fatec.tcc.tccaudit.models.entities.Company;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.repositories.CompanyRepository;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;
import com.fatec.tcc.tccaudit.security.AuthenticationCentral;
import com.fatec.tcc.tccaudit.security.TokenService;
import com.fatec.tcc.tccaudit.services.CompanyService;
import com.fatec.tcc.tccaudit.services.exceptions.CNPJAlreadyRegisteredException;
import com.fatec.tcc.tccaudit.services.exceptions.DatabaseException;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationCentral authenticationCentral;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public LoginAndSignUpDTO signUpCompany(SignUpCompanyDTO signUpCompanyDTO) {
        try {
            Company company = createCompany(signUpCompanyDTO);
            Employee employee = authenticationCentral.cryptography(signUpCompanyDTO.loginDTO(), company);
            companyRepository.save(company);
            employeeRepository.save(employee);
            String token = tokenService.generateToken(employee);
            return new LoginAndSignUpDTO(employee.getIdEmployee(), employee.getName(), employee.getEmail(), token);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(
                    "Error while trying to create company. Please check the provided data.");
        } catch (TransactionSystemException e) {
            throw new DatabaseException("Error while performing database transaction: " + e.getMessage());
        }
    }

    private Company createCompany(SignUpCompanyDTO signUpCompanyDTO) {
        Optional<Company> optionalCompany = companyRepository.findByCnpj(signUpCompanyDTO.companyDTO().cnpj());

        if (optionalCompany.isPresent()) {
            throw new CNPJAlreadyRegisteredException("Este CNPJ já foi cadastrado!");
        }

        if (signUpCompanyDTO.address().getStreet().equals("") || signUpCompanyDTO.address().getCity().equals("")
                || signUpCompanyDTO.address().getState().equals("")) {
            throw new ResourceNotFoundException("CEP não encontrado!");
        }

        Company company = new Company(signUpCompanyDTO.companyDTO().name(), signUpCompanyDTO.companyDTO().cnpj(),
                signUpCompanyDTO.address());
        return company;
    }
}