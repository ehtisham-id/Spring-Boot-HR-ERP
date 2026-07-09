package com.spring.practice1.controller;

import com.spring.practice1.dto.LeaveRequestDTO;
import com.spring.practice1.dto.LeaveResponseDTO;
import com.spring.practice1.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/leaves")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @GetMapping
    public ResponseEntity<List<LeaveResponseDTO>> getLeaves(){
        return leaveService.getAllLeaves();
    }

    @PostMapping
    public ResponseEntity<String> addLeaveType(@Valid @RequestBody LeaveRequestDTO leave){
        return leaveService.addLeaveType(leave);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeaveType(@PathVariable Long id){
        return leaveService.deleteLeaveType(id);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<String> updateLeaveType(@PathVariable Long id, @RequestBody LeaveRequestDTO leave){
        return  leaveService.updateLeaveType(id, leave);
    }
}
