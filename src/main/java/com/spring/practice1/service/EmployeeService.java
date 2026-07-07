package com.spring.practice1.service;

import com.spring.practice1.entity.Employee;
import com.spring.practice1.entity.Leave;
import com.spring.practice1.enums.Reason;
import com.spring.practice1.repository.EmployeeRepository;
import com.spring.practice1.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private  EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    public ArrayList<Employee> findAllEmployee(){
        return (ArrayList<Employee>) employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id){
        Optional<Employee> opt = employeeRepository.findById(id);
        return opt.orElse(null);
    }

    public void addEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public  void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    public void markLeave(Leave leave, Long id){
        employeeRepository.findById(id).ifPresent(employee -> {
            employee.getLeaves().add(leave);
            employeeRepository.save(employee);
        });
    }

    public ArrayList<Leave> getLeaves(){
        return (ArrayList<Leave>) leaveRepository.findAll();
    }
}
