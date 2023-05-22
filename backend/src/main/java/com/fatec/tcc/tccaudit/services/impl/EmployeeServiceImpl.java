package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Department;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;
import com.fatec.tcc.tccaudit.security.AuthenticationCentral;
import com.fatec.tcc.tccaudit.services.EmployeeService;
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
    public String deleteEmployee(Long idEmployee) {
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + idEmployee));
        employeeRepository.delete(employee);
        return "Employee with id " + idEmployee + " has been deleted successfully.";
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
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