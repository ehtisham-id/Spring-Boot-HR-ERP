package com.spring.practice1.dto;

import com.spring.practice1.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicationResponseDTO {
    private Long id;

    private EmployeeResponseDTO employee;

    private String leave_type;

    private Date begin_date;

    private Date end_date;

    private ApplicationStatus status;

    private String reason;
}
