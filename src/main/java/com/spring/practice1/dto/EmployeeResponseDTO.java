package com.spring.practice1.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeResponseDTO {
    private Integer id;

    private String name;

    private Date dob ;

    private Date joining_date ;

    private double salary = 0;

    private UserResponseDTO user;

    private EmployeeResponseDTO approver;
}
