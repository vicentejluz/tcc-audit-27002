package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.fatec.tcc.tccaudit.models.dto.LoginAndSignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.LoginDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpCompanyDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Address;
import com.fatec.tcc.tccaudit.models.entities.Company;
import com.fatec.tcc.tccaudit.models.entities.Department;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.repositories.CompanyRepository;
import com.fatec.tcc.tccaudit.repositories.DepartmentRepository;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;
import com.fatec.tcc.tccaudit.security.AuthenticationCentral;
import com.fatec.tcc.tccaudit.security.TokenService;
import com.fatec.tcc.tccaudit.security.exceptions.InvalidAuthAndSignUpException;
import com.fatec.tcc.tccaudit.services.AuthenticationService;
import com.fatec.tcc.tccaudit.services.exceptions.DatabaseException;
import com.fatec.tcc.tccaudit.services.exceptions.DepartmentNotAllowedException;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationCentral authenticationCentral;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

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
        } catch (BadCredentialsException e) {
            throw new InvalidAuthAndSignUpException("Invalid email or password");
        } catch (TransactionSystemException e) {
            throw new DatabaseException("Error while accessing the database" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public LoginAndSignUpDTO signUpCompany(SignUpCompanyDTO signUpCompanyDTO) {
        Company company = createCompany(signUpCompanyDTO);
        Employee employee = authenticationCentral.cryptography(signUpCompanyDTO.loginDTO(), company);
        employeeRepository.save(employee);
        String token = tokenService.generateToken(employee);
        return new LoginAndSignUpDTO(employee.getIdEmployee(), employee.getName(), employee.getEmail(), token);
    }

    @Override
    @Transactional
    public SignUpDTO signUp(SignUpEmployeeDTO signUpEmployeeDTO) {
        try {
            Employee employee = authenticationCentral.cryptography(signUpEmployeeDTO);
            Long idDepartment = signUpEmployeeDTO.department().getIdDepartment();
            if (idDepartment == 1) {
                throw new DepartmentNotAllowedException("Department not allowed");
            }
            Department department = getDepartment(idDepartment);
            employeeRepository.save(employee);
            return signUpDTO(department, employee);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error while trying to create employee. Please check the provided data.");
        } catch (TransactionSystemException e) {
            throw new DatabaseException("Error while performing database transaction: " + e.getMessage());
        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    private Company createCompany(SignUpCompanyDTO signUpCompanyDTO) {
        try {
            Address address = new Address(signUpCompanyDTO.address().getStreet(), signUpCompanyDTO.address().getCity(),
                    signUpCompanyDTO.address().getState(), signUpCompanyDTO.address().getPostalCode());
            Company company = new Company(signUpCompanyDTO.companyDTO().name(), signUpCompanyDTO.companyDTO().cnpj(),
                    address);
            return companyRepository.save(company);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(
                    "Error while trying to create company. Please check the provided data.");
        } catch (TransactionSystemException e) {
            throw new DatabaseException("Error while performing database transaction: " + e.getMessage());
        }
    }

    private Department getDepartment(Long idDepartment) {
        Optional<Department> department = departmentRepository.findById(idDepartment);
        if (!department.isPresent()) {
            throw new ResourceNotFoundException("Department not found for id: " + idDepartment);
        }
        return department.get();
    }

    private SignUpDTO signUpDTO(Department department, Employee employee) {
        return new SignUpDTO(
                employee.getIdEmployee(),
                employee.getName(),
                employee.getEmail(),
                department.getName(),
                employee.getCompany().getName(),
                employee.getCompany().getCnpj(),
                employee.getCompany().getAddress().getStreet(),
                employee.getCompany().getAddress().getCity(),
                employee.getCompany().getAddress().getState(),
                employee.getCompany().getAddress().getPostalCode());
    }

}