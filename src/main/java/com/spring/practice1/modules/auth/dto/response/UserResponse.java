package com.spring.practice1.modules.auth.dto.response;

import com.spring.practice1.modules.auth.entity.Role;
import com.spring.practice1.modules.auth.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        UserStatus status,
        Role role
) {
}
