package com.fatec.tcc.tccaudit.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.dto.LoginDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Company;
import com.fatec.tcc.tccaudit.models.entities.Department;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.models.entities.Role;
import com.fatec.tcc.tccaudit.repositories.CompanyRepository;
import com.fatec.tcc.tccaudit.repositories.DepartmentRepository;
import com.fatec.tcc.tccaudit.repositories.EmployeeRepository;
import com.fatec.tcc.tccaudit.security.exceptions.InvalidPasswordException;
import com.fatec.tcc.tccaudit.services.exceptions.EmailAlreadyRegisteredException;
import com.fatec.tcc.tccaudit.services.exceptions.InvalidEmployeeNameException;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

@Service
public class AuthenticationCentral {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String ADMIN = "Administrator";

    public Employee cryptography(LoginDTO loginDTO, Company company) {
        validateLoginDTO(loginDTO, company);

        Department department = departmentRepository.findByName(ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException(ADMIN));

        String password = passwordEncoder.encode(loginDTO.password());
        Employee employee = new Employee(ADMIN, loginDTO.email(), password, department, company);
        employee.setRole(Role.ROLE_ADMIN);
        return employee;
    }

    public Employee cryptography(SignUpEmployeeDTO signUpEmployeeDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (signUpEmployeeDTO == null) {
            throw new IllegalArgumentException("SignUpEmployeeDTO cannot be null");
        }
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication cannot be null");
        }

        try {
            Employee auth = (Employee) authentication.getPrincipal();

            Long companyId = auth.getCompany().getIdCompany();

            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new ResourceNotFoundException(companyId));

            if (company == null) {
                throw new ResourceNotFoundException("Company not found");
            }

            validateSignUpEmployeeDTO(signUpEmployeeDTO, company);

            String password = passwordEncoder.encode(signUpEmployeeDTO.loginDTO().password());
            Employee employee = new Employee(signUpEmployeeDTO.name(),
                    signUpEmployeeDTO.loginDTO().email(),
                    password, signUpEmployeeDTO.department(), company);
            employee.setRole(Role.ROLE_EMPLOYEE);
            return employee;
        } catch (ClassCastException e) {
            throw new ResourceNotFoundException("Error while performing authentication: " + e.getMessage());
        }
    }

    private void validateSignUpEmployeeDTO(SignUpEmployeeDTO signUpEmployeeDTO, Company company) {
        String[] adminNames = { "administrator", "administrador", "admin", "superuser", "superadmin", "sysadmin",
                "root", "owner" };

        if (signUpEmployeeDTO == null) {
            throw new IllegalArgumentException("SignUpEmployeeDTO cannot be null");
        }

        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }

        if (Arrays.asList(adminNames).contains(signUpEmployeeDTO.name().toLowerCase())) {
            throw new InvalidEmployeeNameException("The name is not allowed for the employee");
        }

        validateLoginDTO(signUpEmployeeDTO.loginDTO(), company);
    }

    private void validateLoginDTO(LoginDTO loginDTO, Company company) {
        if (loginDTO == null) {
            throw new IllegalArgumentException("loginDTO cannot be null");
        }
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }

        UserDetails email = employeeRepository.findByEmail(loginDTO.email());

        if (email != null) {
            throw new EmailAlreadyRegisteredException("This email is registered! Please, choose another one!");
        }

        if (!isValidPassword(loginDTO.password())) {
            throw new InvalidPasswordException("The password is weak. Please choose a stronger one!");
        }
    }

    private boolean isValidPassword(String password) {
        // Define os critérios da senha
        int minLength = 8;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;
        String specialChars = "!@#$%^&*()-+";

        // Verifica se a senha atende aos critérios
        if (password.length() < minLength) {
            return false;
        }
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (specialChars.indexOf(c) != -1) {
                hasSpecialChar = true;
            }
        }
        return hasUppercase && hasLowercase && hasNumber && hasSpecialChar;
    }
}