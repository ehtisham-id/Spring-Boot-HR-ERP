package com.spring.practice1.controller;

import com.spring.practice1.dto.ApplicationRequestDTO;
import com.spring.practice1.dto.ApplicationResponseDTO;
import com.spring.practice1.dto.ChangeApplicationStatusDTO;
import com.spring.practice1.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> getAll(){
        return ResponseEntity.ok(applicationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getApplicationById(@PathVariable Long id){
        return ResponseEntity.ok(applicationService.geApplicationById(id));
    }

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody ApplicationRequestDTO application){
        applicationService.addApplication(application);
        return ResponseEntity.ok("Added Application Request successfully");
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addUser(@PathVariable Long id ,@Valid @RequestBody ChangeApplicationStatusDTO application){
        applicationService.changeApplicationStatus(id, application);
        return ResponseEntity.ok("Application Status Changed successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> addUser(@PathVariable Long id){
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Deleted  Application Request successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> addUser(@PathVariable Long id,@Valid @RequestBody ApplicationRequestDTO application){
        applicationService.updateApplication(id, application);
        return ResponseEntity.ok("Updated  User successfully");
    }
}
