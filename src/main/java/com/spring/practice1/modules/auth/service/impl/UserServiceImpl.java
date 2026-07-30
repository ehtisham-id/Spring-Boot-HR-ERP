package com.spring.practice1.modules.auth.service.impl;

import com.spring.practice1.modules.auth.dto.request.ResetPasswordRequest;
import com.spring.practice1.modules.auth.dto.request.UserRequest;
import com.spring.practice1.modules.auth.dto.response.TokenResponse;
import com.spring.practice1.modules.auth.dto.response.UserResponse;
import com.spring.practice1.modules.auth.entity.User;
import com.spring.practice1.modules.auth.enums.PermissionType;
import com.spring.practice1.modules.auth.enums.UserStatus;
import com.spring.practice1.modules.auth.mapper.UserMapper;
import com.spring.practice1.modules.auth.repository.UserRepository;
import com.spring.practice1.modules.auth.service.interfaces.EmailOtpService;
import com.spring.practice1.modules.auth.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final CustomUserDetailServiceImpl userDetailsService;
    private final EmailOtpService emailOtpService;

    @Override
    @PreAuthorize("hasAuthority('USER_READ')")
    public List<UserResponse> getUsers() {
        User currentUser = userDetailsService.getCurrentUser();

        if (currentUser.hasPermission(PermissionType.USER_READ_ALL)) {
            return userMapper.getAll(userRepository.findAll());
        }

        return List.of(userMapper.toResponse(currentUser));
    }


    @Override
    public UserResponse getUserById(Long id) {

        User currentUser = userDetailsService.getCurrentUser();
        User target = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found")
        );

        boolean sameUser = currentUser.getId().equals(target.getId());
        boolean canRead = currentUser.hasPermission(PermissionType.USER_READ_ALL);

        if (!sameUser && !canRead) {
            throw new AccessDeniedException("You cannot view this user");
        }

        return userMapper.toResponse(target);
    }


    @Override
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public void updateUser(Long id, UserRequest request) {

        User currentUser = userDetailsService.getCurrentUser();
        User targetUser = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found")
        );


        boolean self = currentUser.getId().equals(targetUser.getId());

        boolean hasPermission = currentUser.hasPermission(PermissionType.USER_UPDATE);

        if (!self && !hasPermission) {
            throw new AccessDeniedException("Cannot update this user");
        }

        if (self && !hasPermission) {
            targetUser.setFirstName(request.firstName());

            targetUser.setLastName(request.lastName());

            if (request.password() != null && !request.password().isBlank()) {
                targetUser.setPassword(encoder.encode(request.password()));
            }

            userRepository.save(targetUser);
            return;
        }

        userMapper.updateEntity(request, targetUser);

        if (request.password() != null && !request.password().isBlank()) {
            targetUser.setPassword(encoder.encode(request.password()));
        }

        userRepository.save(targetUser);
    }


    @Override
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public void deleteUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found")
        );

        userRepository.delete(user);
    }


    @Override
    public void enable2FA(Long id) {

        User currentUser = userDetailsService.getCurrentUser();

        if (!currentUser.getId().equals(id)) {
            throw new AccessDeniedException("You can only change your own 2FA");
        }

        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found")
        );

        user.setMfaEnabled(true);

        userRepository.save(user);
    }


    @Override
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public void changeAccountStatus(Long id, UserStatus status) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found")
        );

        user.setStatus(status);

        userRepository.save(user);
    }


    @Override
    public void resetPassword(Long id, ResetPasswordRequest req) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found")
        );

        emailOtpService.verifyOtp(user, req.otp());
        user.setPassword(encoder.encode(req.password()));
        userRepository.save(user);
    }

    @Override
    public void sendResetEmail(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found")
        );
        emailOtpService.generateAndSendOtp(user);
        userRepository.save(user);
    }
}