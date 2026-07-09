package com.spring.practice1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity()
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private Date dob ;

    @Column(name = "joining_date")
    private Date joining_date ;

    @Column(name = "salary")
    private double salary = 0;
}
