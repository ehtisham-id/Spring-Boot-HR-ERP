package com.spring.practice1.controller;

import com.spring.practice1.dto.EmployeeRequestDTO;
import com.spring.practice1.dto.EmployeeResponseDTO;
import com.spring.practice1.dto.LeaveRequestDTO;
import com.spring.practice1.dto.LeaveResponseDTO;
import com.spring.practice1.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path="/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<List<EmployeeResponseDTO>> findall(){
        return ResponseEntity.ok(employeeService.findAllEmployee());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> findEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @PostMapping()
    public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeRequestDTO employee){
        employeeService.addEmployee(employee);
        return ResponseEntity.ok("Employee created successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee Deleted!");
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<String> markLeave(@Valid @RequestBody LeaveRequestDTO leave, @PathVariable Long id){
        employeeService.markLeave(leave,id);
        return ResponseEntity.ok("Leave Marked successfully!");
    }

    @GetMapping("/leave")
    public ResponseEntity<List<LeaveResponseDTO>> getLeaves(){
        return ResponseEntity.ok(employeeService.getLeaves());
    }
}
