package com.fatec.tcc.tccaudit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.tcc.tccaudit.models.dto.EmployeeDTO;
import com.fatec.tcc.tccaudit.models.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);

    @Query("SELECT new com.fatec.tcc.tccaudit.models.dto.EmployeeDTO(e.idEmployee, e.name, e.email, e.department.name, e.employeeRole, e.isEnabled) FROM Employee e WHERE e.company.idCompany = :idCompany")
    List<EmployeeDTO> findEmployeesByCompany(@Param("idCompany") Long idCompany);

    Optional<Employee> findByIdEmployeeAndCompanyIdCompany(Long idEmployee, Long idCompany);
}