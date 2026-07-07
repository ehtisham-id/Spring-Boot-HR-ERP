package com.spring.practice1.dto;

import com.spring.practice1.enums.Reason;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LeaveRequestDTO {

    private Reason reason;
    @Future
    private Date leave_date;
}
