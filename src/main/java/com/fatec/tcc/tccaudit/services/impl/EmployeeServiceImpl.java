package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.fatec.tcc.tccaudit.models.dto.EmployeeDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Department;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.models.entities.EmployeeRole;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;
import com.fatec.tcc.tccaudit.security.AuthenticationCentral;
import com.fatec.tcc.tccaudit.services.EmployeeService;
import com.fatec.tcc.tccaudit.services.exceptions.AdminAccountBlockException;
import com.fatec.tcc.tccaudit.services.exceptions.DatabaseException;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthenticationCentral authenticationCentral;

    @Override
    @Transactional
    public SignUpDTO signUp(SignUpEmployeeDTO signUpEmployeeDTO) {
        try {
            Employee employee = authenticationCentral.cryptography(signUpEmployeeDTO);

            employeeRepository.save(employee);
            return signUpDTO(employee.getDepartment(), employee);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error while trying to create employee. Please check the provided data.");
        } catch (TransactionSystemException e) {
            throw new DatabaseException("Error while performing database transaction: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public String isEnabled(Long idEmployee) {
        String msg;
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new ResourceNotFoundException(idEmployee));

        if (employee.getEmployeeRole() == EmployeeRole.ADMIN)
            throw new AdminAccountBlockException("It's not allowed to block the account of an administrator employee.");

        if (employee.isEnabled()) {
            employee.setEnabled(false);
            msg = "Funcionário bloqueado com sucesso!";
        } else {
            employee.setEnabled(true);
            msg = "Funcionário desbloqueado com sucesso!";
        }
        employeeRepository.save(employee);

        return msg;
    }

    @Override
    public List<EmployeeDTO> findEmployees() {
        Long idCompany = getCurrentCompanyId();
        return employeeRepository.findEmployeesByCompany(idCompany);
    }

    @Override
    public Employee findById(Long idEmployee) {
        Long idCompany = getCurrentCompanyId();
        return employeeRepository.findByIdEmployeeAndCompanyIdCompany(idEmployee, idCompany)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found."));
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

    private Long getCurrentCompanyId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new NullPointerException(
                    "Authentication is null. Ensure the user is authenticated before accessing the company ID.");
        }
        Long idCompany = ((Employee) authentication.getPrincipal()).getCompany().getIdCompany();
        return idCompany;
    }
}