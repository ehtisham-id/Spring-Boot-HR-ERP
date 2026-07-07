package com.spring.practice1.controller;

import com.spring.practice1.entity.Employee;
import com.spring.practice1.entity.Leave;
import com.spring.practice1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController()
@RequestMapping(path="/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<ArrayList<Employee>> findall(){
        return ResponseEntity.ok(employeeService.findAllEmployee());
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable Long id){
        return employeeService.findEmployeeById(id);
    }

    @PostMapping()
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee){
        employeeService.addEmployee(employee);
        return ResponseEntity.ok("Employee created successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee Deleted!");
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<String> markLeave(@RequestBody Leave leave, @PathVariable Long id){
        employeeService.markLeave(leave,id);
        return ResponseEntity.ok("Leave Marked successfully!");
    }

    @GetMapping("/leave")
    public ArrayList<Leave> getLeaves(){
        return employeeService.getLeaves();
    }
}
