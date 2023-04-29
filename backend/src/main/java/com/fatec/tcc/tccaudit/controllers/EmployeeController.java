package com.fatec.tcc.tccaudit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.dto.SignUpDTO;
import com.fatec.tcc.tccaudit.models.dto.SignUpEmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;
import com.fatec.tcc.tccaudit.services.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpDTO> signUp(@Valid @RequestBody SignUpEmployeeDTO signUpEmployeeDTO) {
        SignUpDTO employee = employeeService.signUp(signUpEmployeeDTO);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/delete/{idEmployee}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long idEmployee) {
        String message = employeeService.deleteEmployee(idEmployee);
        return ResponseEntity.ok(message);
    }
}