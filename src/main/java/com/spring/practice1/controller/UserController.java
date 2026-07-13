package com.spring.practice1.controller;

import com.spring.practice1.dto.UserRequestDTO;
import com.spring.practice1.dto.UserResponseDTO;
import com.spring.practice1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRequestDTO user){
        userService.addUser(user);
        return ResponseEntity.ok("Added User successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> addUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted  User successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> addUser(@PathVariable Long id,@Valid @RequestBody UserRequestDTO user){
        userService.updateUser(id, user);
        return ResponseEntity.ok("Updated  User successfully");
    }
}
