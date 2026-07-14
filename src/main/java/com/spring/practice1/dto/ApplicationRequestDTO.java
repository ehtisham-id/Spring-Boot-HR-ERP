package com.spring.practice1.dto;

import com.spring.practice1.entity.Employee;
import com.spring.practice1.entity.Leave;
import com.spring.practice1.enums.ApplicationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicationRequestDTO {

    private Long employee_id;

    private Long leave_id;

    @Future
    private Date begin_date;

    @Future
    private Date end_date;

    private String status;
}
