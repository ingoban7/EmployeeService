package com.finicity.hr.emp.exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(Integer id) {
        super("Could not find employee " + id);
    }
}
