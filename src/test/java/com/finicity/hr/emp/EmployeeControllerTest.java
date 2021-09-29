package com.finicity.hr.emp;

import com.finicity.hr.emp.model.Employee;
import com.finicity.hr.emp.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class EmployeeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testAddEmployee(){
        log.info("In testAddEmployee Controller");
        Employee employee = Employee.builder().id(1).firstName("Test").lastName("Test").shortId("isningth").email("abc@gmail.com")
                .phone("12345678").address("994 E South Union").role("Software Engineer").build();

        when(employeeService.saveUpdateEmployee(any())).thenReturn(employee);

        ResponseEntity<String> responseEntity =
                this.restTemplate.postForEntity("/api/employees/", employee, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders());
        assertEquals("/"+1, Objects.requireNonNull(responseEntity.getHeaders().get("location")).get(0));
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testAddEmployeeFailure(){
        log.info("In testAddEmployee Failure Controller");
        Employee employee = Employee.builder().id(1).firstName("Test").lastName("Test").shortId("isningth").email("abc@gmail.com")
                .phone("12345678").address("994 E South Union").role("Software Engineer").build();

        when(employeeService.saveUpdateEmployee(any())).thenReturn(null);

        ResponseEntity<String> responseEntity =
                this.restTemplate.postForEntity("/api/employees/", employee, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders());
        assertNull(responseEntity.getBody());
    }

}

