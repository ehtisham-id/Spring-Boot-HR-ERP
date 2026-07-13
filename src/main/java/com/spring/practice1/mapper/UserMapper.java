package com.spring.practice1.mapper;

import com.spring.practice1.dto.UserRequestDTO;
import com.spring.practice1.dto.UserResponseDTO;
import com.spring.practice1.entity.User;
import com.spring.practice1.enums.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO user);
    UserResponseDTO toResponse(User user);

    default UserStatus mapStatus(String status) {
        if (status == null) return null;
        return switch (status.toLowerCase()) {
            case "active" -> UserStatus.ACTIVE;
            case "inactive" -> UserStatus.INACTIVE;
            case "pending" -> UserStatus.PENDING;
            default -> throw new IllegalArgumentException("Unknown status: " + status);
        };
    }

    List<UserResponseDTO> getAll(List<User> users);
    void updateEntity(UserRequestDTO dto, @MappingTarget User entity);
}
