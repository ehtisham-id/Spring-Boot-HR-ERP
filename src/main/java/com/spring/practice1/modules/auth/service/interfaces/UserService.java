package com.spring.practice1.modules.auth.service.interfaces;

import com.spring.practice1.modules.auth.dto.request.ResetPasswordRequest;
import com.spring.practice1.modules.auth.dto.request.UserRequest;
import com.spring.practice1.modules.auth.dto.response.TokenResponse;
import com.spring.practice1.modules.auth.dto.response.UserResponse;
import com.spring.practice1.modules.auth.enums.UserStatus;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();

    UserResponse getUserById(Long id);

    void updateUser(Long id, UserRequest user);

    void deleteUser(Long id);

    void enable2FA(Long id);

    void changeAccountStatus(Long id, UserStatus status);

    void resetPassword(Long id , ResetPasswordRequest req);

    void sendResetEmail(Long id);
}
