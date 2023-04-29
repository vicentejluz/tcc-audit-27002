package com.fatec.tcc.tccaudit.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Department;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.repositories.DepartmentRepository;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;
import com.fatec.tcc.tccaudit.security.AuthenticationCentral;
import com.fatec.tcc.tccaudit.services.EmployeeService;
import com.fatec.tcc.tccaudit.services.exceptions.DatabaseException;
import com.fatec.tcc.tccaudit.services.exceptions.DepartmentNotAllowedException;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthenticationCentral authenticationCentral;

    @Autowired
    private DepartmentRepository departmentRepository;

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