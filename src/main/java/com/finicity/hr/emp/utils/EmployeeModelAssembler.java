package com.finicity.hr.emp.utils;

import com.finicity.hr.emp.api.EmployeeControllerHateos;
import com.finicity.hr.emp.model.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    @Override
    public EntityModel<Employee> toModel(Employee employee) {

        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeControllerHateos.class).getEmployee(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeControllerHateos.class).getEmployees()).withRel("employees"));
    }
}
