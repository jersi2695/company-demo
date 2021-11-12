package com.company.demo.controller;

import com.company.demo.business.EmployeeService;
import com.company.demo.dto.EmployeeDto;
import com.company.demo.dto.ResponseEmployeeDTO;
import com.company.demo.exception.ResourceNotFoundException;
import com.company.demo.mapper.UtilMapper;
import com.company.demo.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UtilMapper utilMapper;

    @PostMapping
    public ResponseEntity<Long> createEmployee(@RequestBody EmployeeDto employeeDto){
        try{
            var employee = employeeService.createEmployee(employeeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(employee.getId());
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{employee-id}")
    public ResponseEntity<ResponseEmployeeDTO> updateEmployee(@PathVariable("employee-id") Long employeeId, @RequestBody EmployeeDto employeeDto){
        try{
            var employee = employeeService.updateEmployee(employeeId, employeeDto);
            return ResponseEntity.ok(utilMapper.map(employee));
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{employee-id}")
    public ResponseEntity deleteEmployee(@PathVariable("employee-id") Long employeeId){
        try{
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.ok().build();
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public List<ResponseEmployeeDTO> listEmployees(@RequestParam(name = "position", required = false) String position, @RequestParam(name = "name", required = false) String name){
        var listEmployees = employeeService.listEmployees(position, name);
        return listEmployees.stream().map(utilMapper::map).collect(Collectors.toList());
    }
}
