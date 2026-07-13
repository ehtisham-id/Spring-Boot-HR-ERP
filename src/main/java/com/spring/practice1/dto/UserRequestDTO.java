package com.spring.practice1.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    @Email
    private String email;

    @Size(min = 3 , max = 25)
    private String f_name;

    @Size(min = 3 , max = 25)
    private String l_name;

    @Column(name = "password")
    @Size(min = 6, max = 50)
    private String password;
}
