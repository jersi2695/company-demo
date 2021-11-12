package com.company.demo.business;

import com.company.demo.dto.EmployeeDto;
import com.company.demo.exception.ResourceNotFoundException;
import com.company.demo.model.Employee;
import com.company.demo.model.Person;
import com.company.demo.repository.EmployeeRepository;
import com.company.demo.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @SpyBean
    EmployeeRepository employeeRepository;

    @SpyBean
    PersonRepository personRepository;

    @Autowired
    EmployeeService employeeService;

    @Before
    public void resetMock(){
        Mockito.clearInvocations(employeeRepository, personRepository);
    }

    @Test
    public void testCreateEmployeeSuccess(){
        var employee = employeeDtoMock();
        var employeeCreated = employeeService.createEmployee(employee);

        Mockito.verify(employeeRepository, Mockito.times(1)).save(any());
        Mockito.verify(personRepository, Mockito.times(1)).save(any());

        assertNotNull(employeeCreated.getId());
        assertEquals(employee.getSalary(), employeeCreated.getSalary());
    }

    @Test
    public void testCreateEmployeeExistPersonSuccess(){
        var person = personRepository.save(personMock());
        var employee = employeeDtoMock();
        employee.setPersonId(person.getId());

        var employeeCreated = employeeService.createEmployee(employee);

        Mockito.verify(employeeRepository, Mockito.times(1)).save(any());
        Mockito.verify(personRepository, Mockito.times(1)).findById(anyLong());

        assertNotNull(employeeCreated.getId());
        assertEquals(employee.getSalary(), employeeCreated.getSalary());
        assertEquals(person.getId(), employeeCreated.getPerson().getId());
    }

    @Test
    public void testCreateEmployeeFails(){
        var employee = employeeDtoMock();
        employee.setPersonId(40L);

        assertThrows(ResourceNotFoundException.class, () ->  employeeService.createEmployee(employee));

        Mockito.verify(employeeRepository, Mockito.times(0)).save(any());
        Mockito.verify(personRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateEmployeeFails(){
        assertThrows(ResourceNotFoundException.class, () ->  employeeService.updateEmployee(99L, EmployeeDto.builder().build()));
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateEmployeeSuccess(){
        var employee = createMockEmployee();
        var employeeDto = employeeDtoMock();
        employeeDto.setPersonLastName("Spain");

        assertNotEquals(employee.getPerson().getLastName(), employeeDto.getPersonLastName());

        var employeeUpdated = employeeService.updateEmployee(employee.getId(), employeeDto);

        assertEquals("Spain", employeeUpdated.getPerson().getLastName());

        Mockito.verify(employeeRepository, Mockito.times(2)).save(any());
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    public void testDeleteEmployeeFails(){
        assertThrows(ResourceNotFoundException.class, () ->  employeeService.deleteEmployee(99L));
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    public void testDeleteEmployeeSuccess(){
        var employee = createMockEmployee();
        employeeService.deleteEmployee(employee.getId());

        Mockito.verify(employeeRepository, Mockito.times(1)).findById(anyLong());
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(anyLong());

        assertFalse(employeeRepository.findById(employee.getId()).isPresent());
    }

    @Test
    public void testListEmployees(){
        employeeRepository.deleteAll();

        var employeeDto = employeeDtoMock();
        IntStream.range(0, 18).forEach(i ->employeeService.createEmployee(employeeDto));

        employeeDto.setPersonName("David");
        IntStream.range(0, 10).forEach(i ->employeeService.createEmployee(employeeDto));

        employeeDto.setPositionName("Engineer");
        IntStream.range(0, 20).forEach(i ->employeeService.createEmployee(employeeDto));

        employeeDto.setPersonName("John");
        IntStream.range(0, 5).forEach(i ->employeeService.createEmployee(employeeDto));

        assertEquals(53, employeeService.listEmployees(null, null).size());
        assertEquals(25, employeeService.listEmployees("Engineer", null).size());
        assertEquals(28, employeeService.listEmployees("Accountant", null).size());
        assertEquals(30, employeeService.listEmployees(null, "David").size());
        assertEquals(23, employeeService.listEmployees(null, "John").size());
        assertEquals(18, employeeService.listEmployees("Accountant", "John").size());
        assertEquals(10, employeeService.listEmployees("Accountant", "David").size());
        assertEquals(20, employeeService.listEmployees("Engineer", "David").size());
        assertEquals(5, employeeService.listEmployees("Engineer", "John").size());
    }

    Employee createMockEmployee(){
        return employeeService.createEmployee(employeeDtoMock());
    }

    public static EmployeeDto employeeDtoMock(){
        return EmployeeDto.builder().personName("John")
                .personLastName("Smith")
                .address("Street 2nd")
                .cellphone("4567891452")
                .cityName("New york")
                .positionName("Accountant")
                .salary(200000).build();
    }


    Person personMock(){
        return new Person("David", "Johnson", "Av 3rd 45", "945874622", "Seattle");
    }
}
