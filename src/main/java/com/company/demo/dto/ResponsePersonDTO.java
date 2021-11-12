package com.company.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePersonDTO {

    private Long id;

    private String name;

    private String lastName;

    private String address;

    private String cellPhone;

    private String cityName;
}
