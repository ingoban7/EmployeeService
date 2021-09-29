package com.finicity.hr.emp.api;

import com.finicity.hr.emp.exception.InvalidInputException;
import com.finicity.hr.emp.model.Employee;
import com.finicity.hr.emp.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path="/", produces = "application/json")
    public ResponseEntity<Object> getAllEmployees(){
        List<Employee> employeeList = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping(path="/{employeeId}", produces = "application/json")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer employeeId){
        log.info("Get Employee by Id");
        if(employeeId == null){
                throw new InvalidInputException("Employee Id is not valid.");
        }
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee){
        if(employee != null && employee.getEmail() == null){
            throw new InvalidInputException("Employee data is not valid.");
        }

        Employee newEmployee = employeeService.saveUpdateEmployee(employee);
        log.info("Employee added.");
        if(newEmployee != null && newEmployee.getId() > 0){
            URI location = new UriTemplate("/{employeeId}").expand(newEmployee.getId());
            return ResponseEntity.created(location).body(Integer.toString(newEmployee.getId()));
        } else{
            log.error("Error while adding employee.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(path="/", consumes = "application/json")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        if(employee == null){
            throw new InvalidInputException("Employee data is not valid.");
        }

        Employee updatedEmployee = employeeService.saveUpdateEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(path="/{employeeId}", consumes = "application/json")
    public ResponseEntity<Integer> deleteEmployee(@PathVariable Integer employeeId){
        if(employeeId == null || employeeId < 0){
            throw new InvalidInputException("Employee id is not valid.");
        }

        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(employeeId);
    }

}
