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
    private EmployeeMapper employeeMapper;

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

    public void updateEmployee(Long id , EmployeeRequestDTO empDto){
        Employee emp = employeeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Employee Not Found")
        );

        employeeMapper.updateEntityFromDto(empDto, emp);
        employeeRepository.save(emp);
    }
}
