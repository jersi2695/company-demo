package com.company.demo.business;

import com.company.demo.repository.EmployeeRepository;
import com.company.demo.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PositionServiceTest {


    @SpyBean
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PositionService positionService;


    @Test
    public void testListPositions(){

        employeeRepository.deleteAll();

        var employeeDto = EmployeeServiceTest.employeeDtoMock();
        IntStream.range(0, 18).forEach(i ->employeeService.createEmployee(employeeDto));

        employeeDto.setPositionName("Engineer");
        IntStream.range(0, 20).forEach(i ->employeeService.createEmployee(employeeDto));

        employeeDto.setPositionName("Architect");
        IntStream.range(0, 5).forEach(i ->employeeService.createEmployee(employeeDto));

        employeeDto.setPositionName("Assistant");
        IntStream.range(0, 14).forEach(i ->employeeService.createEmployee(employeeDto));

        var listPosition = positionService.listPositions();
        Assert.assertEquals(4, listPosition.size());
        Assert.assertEquals(18, listPosition.stream().filter(p -> p.getName().equals("Accountant")).findFirst().get().getEmployees().size());
        Assert.assertEquals(20, listPosition.stream().filter(p -> p.getName().equals("Engineer")).findFirst().get().getEmployees().size());
        Assert.assertEquals(5, listPosition.stream().filter(p -> p.getName().equals("Architect")).findFirst().get().getEmployees().size());
        Assert.assertEquals(14, listPosition.stream().filter(p -> p.getName().equals("Assistant")).findFirst().get().getEmployees().size());

    }

}
