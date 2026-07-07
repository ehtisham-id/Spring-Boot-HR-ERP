package com.spring.practice1.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeRequestDTO {

    @NotBlank(message = "The name Should not be Blank")
    @Size(min = 3 , max=20 , message = "The name Should be between 3 and 20 characters")
    private String name;

    @Past(message = "The DOB Should be past from now")
    private Date dob ;

    @Past(message = "The Joining Date Should be past from now")
    private Date joining_date ;

    @Positive(message = "Salary should be positive")
    private double salary = 0;
}
