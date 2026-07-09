package com.spring.practice1.dto;

import com.spring.practice1.enums.Reason;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LeaveRequestDTO {

    @Size(min=3, max=30 , message="Invalid reason Value")
    private String type;
}
