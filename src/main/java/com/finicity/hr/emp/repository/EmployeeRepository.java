package com.finicity.hr.emp.repository;

import com.finicity.hr.emp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> findByEmail(String email);
}
