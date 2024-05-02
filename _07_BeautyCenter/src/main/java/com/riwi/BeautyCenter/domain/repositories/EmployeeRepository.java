package com.riwi.BeautyCenter.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.BeautyCenter.domain.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
    
}
