package com.company.demo.repository;

import com.company.demo.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

    @Query(value = "Select e FROM Employee e WHERE e.person.name = :personName")
    List<Employee> findAllByPersonName(String personName);

    @Query(value = "Select e FROM Employee e WHERE e.position.name = :positionName")
    List<Employee> findAllByPositionName(String positionName);

    @Query(value = "Select e FROM Employee e WHERE e.person.name = :personName and e.position.name = :positionName")
    List<Employee> findAllByPersonNameAndPositionName(String personName, String positionName);

    List<Employee> findAll();
}
