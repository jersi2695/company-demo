package com.company.demo.business;

import com.company.demo.dto.EmployeeDto;
import com.company.demo.exception.ResourceNotFoundException;
import com.company.demo.model.Employee;
import com.company.demo.model.Person;
import com.company.demo.repository.EmployeeRepository;
import com.company.demo.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionService positionService;
    private final PersonRepository personRepository;

    public Employee createEmployee(EmployeeDto employeeDto){
        Person person;
        if(employeeDto.getPersonId() != null && employeeDto.getPersonId() > 0){
            person = personRepository.findById(employeeDto.getPersonId()).orElseThrow(ResourceNotFoundException::new);
        }else{
            person = personRepository.save(new Person(employeeDto.getPersonName(), employeeDto.getPersonLastName(),
                    employeeDto.getAddress(), employeeDto.getCellphone(), employeeDto.getCityName()));
        }

        var position = positionService.findOrCreatePosition(employeeDto.getPositionName());

        return employeeRepository.save(new Employee(person, position, employeeDto.getSalary()));
    }

    public Employee updateEmployee(Long id, EmployeeDto employeeDto){
        Employee employee = employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        employee.setSalary(employeeDto.getSalary());

        Person person = employee.getPerson();
        person.setName(employeeDto.getPersonName());
        person.setLastName(employeeDto.getPersonLastName());
        person.setAddress(employeeDto.getAddress());
        person.setCellPhone(employeeDto.getCellphone());
        person.setCityName(employeeDto.getCityName());

        employee.setPerson(personRepository.save(person));

        if(!employee.getPosition().getName().equalsIgnoreCase(employeeDto.getPositionName())){
            // If the position is different, try to find or create other position
            var position = positionService.findOrCreatePosition(employeeDto.getPositionName());
            employee.setPosition(position);
        }

        return employeeRepository.save(employee);
    }

    public boolean deleteEmployee(Long id){
        employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        employeeRepository.deleteById(id);
        return true;
    }

    public List<Employee> listEmployees(String position, String name){
        if(!StringUtils.isBlank(position) && !StringUtils.isBlank(name)){
            return employeeRepository.findAllByPersonNameAndPositionName(name, position);
        }else if(!StringUtils.isBlank(position)){
            return employeeRepository.findAllByPositionName(position);
        }else if(!StringUtils.isBlank(name)){
            return employeeRepository.findAllByPersonName(name);
        }else{
            return employeeRepository.findAll();
        }
    }
}
