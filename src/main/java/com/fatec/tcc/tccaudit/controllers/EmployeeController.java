package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.dto.EmployeeDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.services.EmployeeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "bearer-key")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> findEmployees() {
        List<EmployeeDTO> employees = employeeService.findEmployees();
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping(value = "/employee/{idEmployee}")
    public ResponseEntity<Employee> findById(@PathVariable Long idEmployee) {
        Employee employee = employeeService.findById(idEmployee);
        return ResponseEntity.ok().body(employee);

    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpDTO> signUp(@Valid @RequestBody SignUpEmployeeDTO signUpEmployeeDTO) {
        SignUpDTO employee = employeeService.signUp(signUpEmployeeDTO);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/is-enabled/{idEmployee}")
    public ResponseEntity<String> isEnabled(@PathVariable Long idEmployee) {
        String message = employeeService.isEnabled(idEmployee);
        return ResponseEntity.ok(message);
    }
}