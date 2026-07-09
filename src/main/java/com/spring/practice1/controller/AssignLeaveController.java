package com.spring.practice1.controller;

import com.spring.practice1.dto.AssignLeaveRequestDTO;
import com.spring.practice1.dto.AssignLeaveResponseDTO;
import com.spring.practice1.service.AssignLeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignes")
@Tag(name = "Assign Leaves Rest API ", description = "API Endpoint to Assign Leaves to Employees")
public class AssignLeaveController {
    @Autowired
    private AssignLeaveService assignLeaveService;

    @GetMapping
    @Operation(summary = "Get all Assigned Leaves")
    public ResponseEntity<List<AssignLeaveResponseDTO>> getAssignedLeaves(){
        return ResponseEntity.ok(assignLeaveService.getAssignedLeaves());
    }

    @PostMapping
    @Operation(summary = "Assign New Leave")
    public ResponseEntity<String> assignLeave(@RequestBody AssignLeaveRequestDTO leave){
        assignLeaveService.assignLeave(leave);
        return  ResponseEntity.ok("Leave assigned Successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Assigned Leave By id")
    public ResponseEntity<String> updateAssignedLeave(@PathVariable Long id ,@RequestBody AssignLeaveRequestDTO leaves){
        assignLeaveService.updateLeave(id, leaves);
        return ResponseEntity.ok("Updated Successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Assigned Leave By id")
    public ResponseEntity<String> deleteAssignedLeave(@PathVariable Long id){
        assignLeaveService.deleteLeave(id);
        return ResponseEntity.ok("Removed Assigned Leave Successfully");
    }
}
