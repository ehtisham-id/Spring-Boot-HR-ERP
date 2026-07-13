package com.spring.practice1.dto;

import com.spring.practice1.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicationResponseDTO {
    private Long id;

    private Long employee_id;

    private String employee_name;

    private String leave_type;

    private Date start_date;

    private Date end_date;

    private ApplicationStatus status;
}
