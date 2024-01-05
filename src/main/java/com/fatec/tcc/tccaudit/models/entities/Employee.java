package com.fatec.tcc.tccaudit.models.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_employee", uniqueConstraints = @UniqueConstraint(name = "uk_email", columnNames = "email"))
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;

    @NotBlank
    @Column(length = 60)
    private String name;

    @Email
    @Column(unique = true, length = 60)
    @NotBlank
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private EmployeeRole employeeRole;

    private boolean isEnabled;

    @ManyToOne()
    @JoinColumn(name = "id_company", nullable = false, foreignKey = @ForeignKey(name = "fk_employee_id_company"))
    private Company company;

    @ManyToOne()
    @JoinColumn(name = "id_department", nullable = false, foreignKey = @ForeignKey(name = "fk_employee_id_department"))
    private Department department;

    public Employee() {
    }

    public Employee(@NotBlank String name, @Email @NotBlank String email, @NotBlank String password,
            Department department, Company company) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.company = company;
        this.isEnabled = true;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (employeeRole == EmployeeRole.ADMIN)
            return List.of(new SimpleGrantedAuthority(EmployeeRole.ADMIN.getEmployeeRole()),
                    new SimpleGrantedAuthority(EmployeeRole.EMPLOYEE.getEmployeeRole()));
        else
            return List.of(new SimpleGrantedAuthority(EmployeeRole.EMPLOYEE.getEmployeeRole()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}