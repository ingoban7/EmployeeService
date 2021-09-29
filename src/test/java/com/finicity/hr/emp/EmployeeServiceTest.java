package com.finicity.hr.emp;


import com.finicity.hr.emp.model.Employee;
import com.finicity.hr.emp.repository.EmployeeRepository;
import com.finicity.hr.emp.service.EmployeeService;
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

    @Test
    void contextLoads() {
        assertThat(employeeService).isNotNull();
        assertThat(employeeRepository).isNotNull();
    }

    @Test
    public void testAddEmployee(){

        Employee employee = Employee.builder().id(1).firstName("Test").lastName("Test").shortId("isningth").email("abc@gmail.com")
                .phone("12345678").address("994 E South Union").role("Software Engineer").build();
        when(employeeRepository.save(employee)).thenReturn(employee);
        assertEquals(employee, employeeService.saveUpdateEmployee(employee));

    }

    @Test
    public void testGetAllEmployees(){
        when(employeeRepository.findAll()).thenReturn(Stream.of(
                Employee.builder().id(1).firstName("Test").lastName("Test").shortId("isningth").email("abc@gmail.com")
                        .phone("12345678").address("994 E South Union").role("HR").build(),
                Employee.builder().id(2).firstName("Brian").lastName("Kobe").shortId("bkobe").email("abc@gmail.com")
                        .phone("12345678").address("994 E South Union").role("Software Engineer").build())
                .collect(Collectors.toList()));

        assertEquals(2, employeeService.getAllEmployees().size());

    }

    @Test
    public void testGetEmployeeByEmail(){
        String email = "abc@gmail.com";

        when(employeeRepository.findByEmail(email)).thenReturn(
                Stream.of(Employee.builder().id(1).firstName("Test").lastName("Test").shortId("isningth").email("abc@gmail.com")
                        .phone("12345678").address("994 E South Union").role("Software Engineer").build()).collect(Collectors.toList()));

        assertEquals(1, employeeService.getEmployeeByEmail(email).size());
    }

    @Test
    public void testDeleteEmployee(){
        Employee employee = Employee.builder().id(1).firstName("Test").lastName("Test").shortId("isningth").email("abc@gmail.com")
                .phone("12345678").address("994 E South Union").role("Software Engineer").build();

        employeeService.deleteEmployee(employee.getId());
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }
}
