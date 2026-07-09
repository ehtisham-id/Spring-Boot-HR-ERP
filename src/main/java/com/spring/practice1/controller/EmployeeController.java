package com.spring.practice1.controller;

import com.spring.practice1.dto.EmployeeRequestDTO;
import com.spring.practice1.dto.EmployeeResponseDTO;
import com.spring.practice1.dto.LeaveRequestDTO;
import com.spring.practice1.dto.LeaveResponseDTO;
import com.spring.practice1.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path="/employee")
@Tag(name = "Employee Rest API", description = "Handle Employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    @Operation(summary = "Get All employees")
    public ResponseEntity<List<EmployeeResponseDTO>> findall(){
        return ResponseEntity.ok(employeeService.findAllEmployee());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Delete Employee By id")
    public ResponseEntity<EmployeeResponseDTO> findEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @PostMapping()
    @Operation(summary = "Create new Employee")
    public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeRequestDTO employee){
        employeeService.addEmployee(employee);
        return ResponseEntity.ok("Employee created successfully!");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Employee Type By id")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee Deleted!");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Employee By id")
    public ResponseEntity<String> updateEmplyeeById(@PathVariable Long id,@Valid @RequestBody EmployeeRequestDTO emp) {
        employeeService.updateEmployee(id, emp);
        return ResponseEntity.ok("Employee updated successfully");
    }
}
