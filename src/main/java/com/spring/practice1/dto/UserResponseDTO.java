package com.spring.practice1.dto;

import com.spring.practice1.modules.auth.enums.UserStatus;
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
