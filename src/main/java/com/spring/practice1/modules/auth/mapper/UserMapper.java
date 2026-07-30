package com.spring.practice1.modules.auth.mapper;


import com.spring.practice1.modules.auth.dto.request.UserRequest;
import com.spring.practice1.modules.auth.dto.response.TokenResponse;
import com.spring.practice1.modules.auth.dto.response.UserResponse;
import com.spring.practice1.modules.auth.entity.User;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {


    UserResponse toResponse(User user);


    List<UserResponse> getAll(List<User> users);



    @Mapping(target = "role", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntity(
            UserRequest request,
            @MappingTarget User user
    );
}