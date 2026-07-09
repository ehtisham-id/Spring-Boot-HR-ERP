package com.spring.practice1.dto;

import com.spring.practice1.enums.Reason;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LeaveResponseDTO {
    private Integer id;
    private String type;
}
