package com.finicity.hr.emp;

import com.finicity.hr.emp.model.Employee;
import com.finicity.hr.emp.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveReadDelete(){
        Employee employee = Employee.builder().id(1).firstName("Test").lastName("Test").shortId("isningth").email("abc@gmail.com")
                .phone("12345678").address("994 E South Union").role("Software Engineer").build();

        employeeRepository.save(employee);
        Iterable<Employee> employees = employeeRepository.findAll();
        assertThat(employees).extracting(Employee::getEmail).containsOnly("abc@gmail.com");

        Iterable<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());
        assertThat(employeeList).extracting(Employee::getShortId).containsOnly("isningth");

        employeeRepository.deleteById(employee.getId());
        assertThat(employeeRepository.findAll()).isEmpty();

    }
}
