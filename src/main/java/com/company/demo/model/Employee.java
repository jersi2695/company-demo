package com.company.demo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    private Integer salary;

    public Employee(Person person, Position position, Integer salary) {
        this.person = person;
        this.position = position;
        this.salary = salary;
    }
}
