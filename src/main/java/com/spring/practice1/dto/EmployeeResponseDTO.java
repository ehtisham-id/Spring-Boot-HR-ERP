package com.spring.practice1.dto;

import com.spring.practice1.entity.Leave;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EmployeeResponseDTO {
    private Integer id;

    private String name;

    private Date dob ;

    private Date joining_date ;

    private double salary = 0;

    private List<LeaveResponseDTO> leaves;
}
