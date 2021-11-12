package com.company.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {

    private Long personId;

    private String personName;

    private String personLastName;

    private String address;

    private String cellphone;

    private String cityName;

    private String positionName;

    private Integer salary;
}
