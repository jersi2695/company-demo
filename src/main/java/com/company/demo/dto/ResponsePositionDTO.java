package com.company.demo.dto;

import com.company.demo.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePositionDTO {

    private Long id;

    private String name;

    private List<ResponseEmployeeDTO> employees;
}
