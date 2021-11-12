package com.company.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    private String address;

    private String cellPhone;

    private String cityName;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Employee> employees;

    public Person(String name, String lastName, String address, String cellPhone, String cityName) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.cellPhone = cellPhone;
        this.cityName = cityName;
    }
}
