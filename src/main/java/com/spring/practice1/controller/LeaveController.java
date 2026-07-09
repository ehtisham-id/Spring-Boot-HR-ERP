package com.spring.practice1.controller;

import com.spring.practice1.dto.LeaveRequestDTO;
import com.spring.practice1.dto.LeaveResponseDTO;
import com.spring.practice1.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/leaves")
@Tag(name = "Leave Type API" , description = "Handle all leave types")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @GetMapping
    @Operation(summary = "Get All Leaves Types")
    public ResponseEntity<List<LeaveResponseDTO>> getLeaves(){
        return leaveService.getAllLeaves();
    }

    @PostMapping
    @Operation(summary = "Create new Leave type")
    public ResponseEntity<String> addLeaveType(@Valid @RequestBody LeaveRequestDTO leave){
        return leaveService.addLeaveType(leave);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Leave Type By id")
    public ResponseEntity<String> deleteLeaveType(@PathVariable Long id){
        return leaveService.deleteLeaveType(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Leave Type By id" , method = "Put")
    public  ResponseEntity<String> updateLeaveType(@PathVariable Long id, @RequestBody LeaveRequestDTO leave){
        return  leaveService.updateLeaveType(id, leave);
    }
}
