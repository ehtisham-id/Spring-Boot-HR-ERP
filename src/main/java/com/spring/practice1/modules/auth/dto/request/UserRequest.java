package com.spring.practice1.modules.auth.dto.request;

import com.spring.practice1.modules.auth.entity.Role;
import com.spring.practice1.modules.auth.enums.RoleType;
import com.spring.practice1.modules.auth.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public record UserRequest(
        @Size(min = 3, max = 25)
        String firstName,
        @Size(min = 3, max = 25)
        String lastName,
        @Email
        String email,
        @Size(min = 6, max = 20)
        String password,
        UserStatus status,
        RoleType role
) {}