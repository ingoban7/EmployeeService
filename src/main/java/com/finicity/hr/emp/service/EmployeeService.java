package com.finicity.hr.emp.service;

import com.finicity.hr.emp.model.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Employee Service
 */
public interface EmployeeService {

    /**
     * Gets employee by Id
     * @param employeeId employee id
     * @return employee
     */
    Optional<Employee> getEmployeeById(Integer employeeId);

    /**
     * Gets all employees
     * @return employees
     */
    List<Employee> getAllEmployees();

    /**
     * Gets employees by First and Last Name
     * @param firstName first name
     * @param lastName last name
     * @return list of employees
     */
    List<Employee> getEmployeeByName(String firstName, String lastName);

    /**
     * Gets employee by email
     * @param email email
     * @return list of employees
     */
    List<Employee> getEmployeeByEmail(String email);

    /**
     * Adds or updates employee
     * @param employee employee
     * @return employee
     */
    Employee saveUpdateEmployee(Employee employee);

    /**
     * Deletes employee
     * @param employeeId employee Id
     */
    void deleteEmployee(Integer employeeId);

}
