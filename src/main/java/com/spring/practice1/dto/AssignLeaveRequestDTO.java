package com.spring.practice1.dto;

import com.spring.practice1.entity.Employee;
import com.spring.practice1.entity.Leave;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AssignLeaveRequestDTO {

    @Positive
    private Long employee_id;

    @Positive
    private  Long leave_id;

    private Date begin_date;

    private Date end_date;

    @PositiveOrZero
    private Integer max_leaves;
}
