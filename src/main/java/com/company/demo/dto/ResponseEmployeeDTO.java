package com.company.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEmployeeDTO {

    private Long id;

    private ResponsePersonDTO person;

    private ResponsePositionDTO position;

    private Integer salary;
}
