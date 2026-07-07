package com.spring.practice1.service;

import com.spring.practice1.dto.EmployeeRequestDTO;
import com.spring.practice1.dto.EmployeeResponseDTO;
import com.spring.practice1.dto.LeaveRequestDTO;
import com.spring.practice1.dto.LeaveResponseDTO;
import com.spring.practice1.entity.Employee;
import com.spring.practice1.entity.Leave;
import com.spring.practice1.mapper.EmployeeMapper;
import com.spring.practice1.mapper.LeaveMapper;
import com.spring.practice1.repository.EmployeeRepository;
import com.spring.practice1.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private  EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private LeaveMapper leaveMapper;

    public List<EmployeeResponseDTO> findAllEmployee(){
        return employeeMapper.listToResponse(employeeRepository.findAll());
    }

    public EmployeeResponseDTO findEmployeeById(Long id){
        Employee emp = employeeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Employee Not Found")
        );
        return employeeMapper.toResponse(emp);
    }

    public void addEmployee(EmployeeRequestDTO employee){
        Employee emp = employeeMapper.toEntity(employee);
        employeeRepository.save(emp);
    }

    public  void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    public void markLeave(LeaveRequestDTO leave, Long id){
        Leave l = leaveMapper.toEntity(leave);
        employeeRepository.findById(id).ifPresent(employee -> {
            employee.getLeaves().add(l);
            employeeRepository.save(employee);
        });
    }

    public List<LeaveResponseDTO> getLeaves(){
        return leaveMapper.listToResponse(leaveRepository.findAll());
    }
}
