package com.spring.practice1.service;

import com.spring.practice1.dto.ApplicationRequestDTO;
import com.spring.practice1.dto.ApplicationResponseDTO;
import com.spring.practice1.entity.Application;
import com.spring.practice1.entity.Employee;
import com.spring.practice1.entity.Leave;
import com.spring.practice1.mapper.ApplicationMapper;
import com.spring.practice1.repository.ApplicationRepository;
import com.spring.practice1.repository.EmployeeRepository;
import com.spring.practice1.repository.LeaveRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    public List<ApplicationResponseDTO> getAll(){
        return applicationMapper.getAll(applicationRepository.findAll());
    }

    public ApplicationResponseDTO geApplicationById(Long id){
        return applicationMapper.toResponse(applicationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User Not Found")
        ));
    }

    public void addApplication(@NonNull ApplicationRequestDTO application){
        Employee emp = employeeRepository.findById(application.getEmployee_id()).orElseThrow(
                ()->new RuntimeException("User with Id not found")
        );

        Leave leave = leaveRepository.findById(application.getLeave_id()).orElseThrow(
                ()->new RuntimeException("Leave Type with Id not found")
        );

        LocalDate today = LocalDate.now();

        LocalDate start = application.getStart_date()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate end = application.getEnd_date()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if (!start.isAfter(today)) {
            throw new RuntimeException("Leave must start after today");
        }

        if (start.isAfter(end)) {
            throw new RuntimeException("Start date must not be after end date");
        }

        Application temp = applicationMapper.toEntity(application);
        temp.setEmployee(emp);
        temp.setLeave(leave);
        applicationRepository.save(temp);
    }

    public void deleteApplication(Long id){
        applicationRepository.deleteById(id);
    }
}
