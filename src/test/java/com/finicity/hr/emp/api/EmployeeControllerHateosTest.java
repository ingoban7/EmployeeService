package com.finicity.hr.emp.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.finicity.hr.emp.model.Employee;
import com.finicity.hr.emp.service.EmployeeService;
import com.finicity.hr.emp.utils.EmployeeModelAssembler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;

import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.CoreMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeControllerHateos.class)
@Import({ EmployeeModelAssembler.class })
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class EmployeeControllerHateosTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired private ObjectMapper mapper;

    private final Employee emp = Employee.builder().id(1).firstName("Test").lastName("Test").shortId("isningth").email("abc@gmail.com")
                .phone("12345678").address("994 E South Union").role("HR").build();
    private final Employee emp2 = Employee.builder().id(2).firstName("Brian").lastName("Kobe").shortId("bkobe").email("test@gmail.com")
            .phone("12345678").address("994 E South Union").role("Software Engineer").build();

    @Test
    public void testGetAllEmployees() throws Exception{
        when(employeeService.getAllEmployees()).thenReturn(Stream.of(
                emp, emp2).collect(Collectors.toList()));

        mockMvc.perform(get("/employees").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.employeeList[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.employeeList[0].firstName", is("Test")))
                .andExpect(jsonPath("$._embedded.employeeList[0].lastName", is("Test")))
                .andExpect(jsonPath("$._embedded.employeeList[0].email", is("abc@gmail.com")))
                .andExpect(jsonPath("$._embedded.employeeList[0].role", is("HR")))
                .andExpect(jsonPath("$._embedded.employeeList[0]._links.self.href", is("http://localhost/employees/1")))
                .andExpect(jsonPath("$._embedded.employeeList[0]._links.employees.href", is("http://localhost/employees")))
                .andExpect(jsonPath("$._embedded.employeeList[1].id", is(2)))
                .andExpect(jsonPath("$._embedded.employeeList[1].firstName", is("Brian")))
                .andExpect(jsonPath("$._embedded.employeeList[1].lastName", is("Kobe")))
                .andExpect(jsonPath("$._embedded.employeeList[1].email", is("test@gmail.com")))
                .andExpect(jsonPath("$._embedded.employeeList[1].role", is("Software Engineer")))
                .andExpect(jsonPath("$._embedded.employeeList[1]._links.self.href", is("http://localhost/employees/2")))
                .andExpect(jsonPath("$._embedded.employeeList[1]._links.employees.href", is("http://localhost/employees")))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/employees"))) //
                .andReturn();

    }

    @Test
    public void testAddEmployeesSuccess() throws Exception{

        when(employeeService.saveUpdateEmployee(any())).thenReturn(emp);

          mockMvc.perform(
                        post("/employees")
                                .content(mapper.writeValueAsBytes(emp))
                                .contentType(MediaTypes.HAL_JSON_VALUE))
                  .andDo(print())
                  .andExpect(status().isCreated())
                  .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                  .andExpect(jsonPath("$.id", is(1)))
                  .andExpect(jsonPath("$.firstName", is("Test")))
                  .andExpect(jsonPath("$.lastName", is("Test")))
                  .andExpect(jsonPath("$.email", is("abc@gmail.com")))
                  .andExpect(jsonPath("$.role", is("HR")))
                  .andExpect(jsonPath("$._links.self.href", is("http://localhost/employees/1")))
                  .andExpect(jsonPath("$._links.employees.href", is("http://localhost/employees")))
                  .andReturn();
    }

    @Test
    public void testAddEmployeesFailure() throws Exception {

        when(employeeService.saveUpdateEmployee(any())).thenReturn(emp);

        mockMvc.perform(
                post("/employees")
                        .content(mapper.writeValueAsBytes(null))
                        .contentType(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void deleteEmployeeById() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1);
        mockMvc.perform(
                delete("/employees/{id}", "1"))
                .andExpect(status().isNoContent());
    }

}
