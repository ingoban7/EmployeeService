package com.finicity.hr.emp.api;

import com.finicity.hr.emp.exception.EmployeeNotFoundException;
import com.finicity.hr.emp.exception.InvalidInputException;
import com.finicity.hr.emp.model.Employee;

import com.finicity.hr.emp.service.EmployeeService;
import com.finicity.hr.emp.utils.EmployeeModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeControllerHateos {

    private final EmployeeService employeeService;

    private final EmployeeModelAssembler employeeModelAssembler;

    public EmployeeControllerHateos(EmployeeService employeeService, EmployeeModelAssembler employeeModelAssembler){
        this.employeeService = employeeService;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> getEmployees() {

        List<EntityModel<Employee>> employees = employeeService.getAllEmployees().stream()
                .map(employeeModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeControllerHateos.class).getEmployees()).withSelfRel());
    }

    @PostMapping("/employees")
    public ResponseEntity<EntityModel<Employee>> addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.saveUpdateEmployee(employee);

        return ResponseEntity
                .created(linkTo(methodOn(EmployeeControllerHateos.class).getEmployee(newEmployee.getId())).toUri()) //
                .body(employeeModelAssembler.toModel(newEmployee));
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> getEmployee(@PathVariable Integer id) {

        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeModelAssembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Object> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id) {

        Employee updatedEmployee = employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setFirstName(newEmployee.getFirstName());
                    employee.setLastName(newEmployee.getLastName());
                    employee.setShortId(newEmployee.getShortId());
                    employee.setEmail(newEmployee.getEmail());
                    employee.setPhone(newEmployee.getPhone());
                    employee.setAddress(newEmployee.getAddress());
                    employee.setRole(newEmployee.getRole());
                    return employeeService.saveUpdateEmployee(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return employeeService.saveUpdateEmployee(newEmployee);
                });

        EntityModel<Employee> entityModel = employeeModelAssembler.toModel(updatedEmployee);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id) {
        if(id < 0 )
            throw new InvalidInputException("Invalid employee id.");
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
