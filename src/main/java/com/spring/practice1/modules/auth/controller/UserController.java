package com.spring.practice1.modules.auth.controller;

import com.spring.practice1.modules.auth.dto.request.ResetPasswordRequest;
import com.spring.practice1.modules.auth.dto.request.UserRequest;
import com.spring.practice1.modules.auth.dto.response.TokenResponse;
import com.spring.practice1.modules.auth.dto.response.UserResponse;
import com.spring.practice1.modules.auth.enums.UserStatus;
import com.spring.practice1.modules.auth.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted  User successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> addUser(@PathVariable Long id,@Valid @RequestBody UserRequest user){
        userService.updateUser(id, user);
        return ResponseEntity.ok("Updated  User successfully");
    }

    @PutMapping("/mfa-enabled")
    public ResponseEntity<?> enableMFA(){
        return ResponseEntity.ok("Coming Soon");
    }

    @PutMapping("/account-status/{id}")
    public ResponseEntity<?> accountStatus(@PathVariable Long id,@RequestParam UserStatus status){
        userService.changeAccountStatus(id, status);
        return ResponseEntity.ok("Status Changed");
    }

    @GetMapping("/reset-password/{id}")
    public ResponseEntity<?> accountStatus(@PathVariable Long id){
        userService.sendResetEmail(id);
        return ResponseEntity.ok("OTP Sent Successfully");
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> accountStatus(@PathVariable Long id,@RequestBody ResetPasswordRequest req){
        userService.resetPassword(id, req);
        return ResponseEntity.ok("Password Reset Successfully");
    }
}
