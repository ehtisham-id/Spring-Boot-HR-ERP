package com.spring.practice1.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AssignLeaveResponseDTO {
    private Integer Id;

    private EmployeeResponseDTO employee;

    private  LeaveResponseDTO leave;

    private Date begin_date;

    private Date end_date;

    private Integer max_leaves;
}
