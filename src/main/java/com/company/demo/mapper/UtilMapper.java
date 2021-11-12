package com.company.demo.mapper;

import com.company.demo.dto.ResponseEmployeeDTO;
import com.company.demo.dto.ResponsePersonDTO;
import com.company.demo.dto.ResponsePositionDTO;
import com.company.demo.model.Employee;
import com.company.demo.model.Position;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UtilMapper {


    public ResponseEmployeeDTO map(Employee employee){
        return ResponseEmployeeDTO.builder().id(employee.getId()).salary(employee.getSalary())
                .position(ResponsePositionDTO.builder()
                        .id(employee.getPosition().getId())
                        .name(employee.getPosition().getName())
                        .build())
                .person(ResponsePersonDTO.builder()
                        .id(employee.getPerson().getId())
                        .name(employee.getPerson().getName())
                        .lastName(employee.getPerson().getLastName())
                        .address(employee.getPerson().getAddress())
                        .cellPhone(employee.getPerson().getCellPhone())
                        .cityName(employee.getPerson().getCityName())
                        .build()).build();
    }

    public ResponsePositionDTO map(Position position){

        var employees = position.getEmployees().stream().map(employee ->
                ResponseEmployeeDTO.builder().id(employee.getId()).salary(employee.getSalary())
                        .person(ResponsePersonDTO.builder()
                                .id(employee.getPerson().getId())
                                .name(employee.getPerson().getName())
                                .lastName(employee.getPerson().getLastName())
                                .address(employee.getPerson().getAddress())
                                .cellPhone(employee.getPerson().getCellPhone())
                                .cityName(employee.getPerson().getCityName())
                                .build()).build()).collect(Collectors.toList());

        return ResponsePositionDTO.builder()
                .id(position.getId())
                .name(position.getName())
                .employees(employees)
                .build();
    }
}
