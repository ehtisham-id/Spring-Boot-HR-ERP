package com.spring.practice1.dto;

import com.spring.practice1.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id ;

    private String email;

    private String f_name;

    private String l_name;

    private UserStatus status;
}
