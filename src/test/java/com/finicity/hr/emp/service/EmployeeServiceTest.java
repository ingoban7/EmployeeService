package com.finicity.hr.emp.service;


import com.finicity.hr.emp.model.Employee;
import com.finicity.hr.emp.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    private final Employee emp = Employee.builder().id(1).firstName("Test").lastName("Test").shortId("isningth").email("abc@gmail.com")
            .phone("12345678").address("994 E South Union").role("HR").build();
    private final Employee emp2 = Employee.builder().id(2).firstName("Brian").lastName("Kobe").shortId("bkobe").email("test@gmail.com")
            .phone("12345678").address("994 E South Union").role("Software Engineer").build();

    @Test
    void contextLoads() {
        assertThat(employeeService).isNotNull();
        assertThat(employeeRepository).isNotNull();
    }

    @Test
    public void testAddEmployee(){

        when(employeeRepository.save(emp)).thenReturn(emp);
        assertEquals(emp, employeeService.saveUpdateEmployee(emp));

    }

    @Test
    public void testGetAllEmployees(){
        when(employeeRepository.findAll()).thenReturn(Stream.of(emp, emp2)
                .collect(Collectors.toList()));

        assertEquals(2, employeeService.getAllEmployees().size());

    }

    @Test
    public void testGetEmployeeByEmail(){
        String email = "abc@gmail.com";

        when(employeeRepository.findByEmail(email)).thenReturn(
                Stream.of(emp).collect(Collectors.toList()));

        assertEquals(1, employeeService.getEmployeeByEmail(email).size());
    }

    @Test
    public void testDeleteEmployee(){
        employeeService.deleteEmployee(emp.getId());
        verify(employeeRepository, times(1)).deleteById(emp.getId());
    }
}
