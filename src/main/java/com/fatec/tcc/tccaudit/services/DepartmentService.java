package com.fatec.tcc.tccaudit.services;

import java.util.List;

import com.fatec.tcc.tccaudit.models.entities.Department;

public interface DepartmentService {
    List<Department> findAll();
}