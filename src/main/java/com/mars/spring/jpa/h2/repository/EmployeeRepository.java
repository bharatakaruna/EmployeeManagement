package com.mars.spring.jpa.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mars.spring.jpa.h2.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
