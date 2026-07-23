package com.spring.practice1.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeApplicationStatusDTO {
    @PositiveOrZero
    Long approver_id;
    String status;
    String reason;
}
