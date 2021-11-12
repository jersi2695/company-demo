package com.company.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Position {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "position", fetch = FetchType.EAGER)
    @OrderBy(value = "salary DESC")
    private List<Employee> employees;


    public Position(String name){
        this.name = name;
    }
}
