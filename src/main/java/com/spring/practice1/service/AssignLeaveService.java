package com.spring.practice1.service;

import com.spring.practice1.dto.AssignLeaveRequestDTO;
import com.spring.practice1.dto.AssignLeaveResponseDTO;
import com.spring.practice1.entity.AssignLeave;
import com.spring.practice1.entity.Employee;
import com.spring.practice1.entity.Leave;
import com.spring.practice1.mapper.AssignLeaveMapper;
import com.spring.practice1.mapper.LeaveMapper;
import com.spring.practice1.repository.AssignLeaveRepository;
import com.spring.practice1.repository.EmployeeRepository;
import com.spring.practice1.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignLeaveService {
    @Autowired
    private AssignLeaveRepository assignLeaveRepository;

    @Autowired
    private AssignLeaveMapper assignLeaveMapper;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<AssignLeaveResponseDTO> getAssignedLeaves(){
        return assignLeaveMapper.listToDto(assignLeaveRepository.findAll());
    }

    public void assignLeave(AssignLeaveRequestDTO assign_leave){
        Leave leave = leaveRepository.findById(assign_leave.getLeave_id()).orElseThrow(
                () -> new RuntimeException("Leave Type Not Found")
        );

        Employee employee = employeeRepository.findById(assign_leave.getEmployee_id()).orElseThrow(
                ()->new RuntimeException("Employee Not Found")
        );


        if(assign_leave.getBegin_date().after(assign_leave.getEnd_date())){
            throw new RuntimeException("Start Date must be Before End Date");
        }

        AssignLeave temp = assignLeaveMapper.toEntity(assign_leave);
        temp.setEmployee(employee);
        temp.setLeave(leave);
        assignLeaveRepository.save(temp);
    }

    public void deleteLeave(Long id){
        AssignLeave leave = assignLeaveRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Leave Not Assigned Yet")
        );
        assignLeaveRepository.deleteById(id);
    }

    public void updateLeave(Long id , AssignLeaveRequestDTO assign_leave){
        AssignLeave temp = assignLeaveRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Assigned Leave not Found")
        );

        Leave leave = leaveRepository.findById(assign_leave.getLeave_id()).orElseThrow(
                () -> new RuntimeException("Leave Type Not Found")
        );

        Employee employee = employeeRepository.findById(assign_leave.getEmployee_id()).orElseThrow(
                ()->new RuntimeException("Employee Not Found")
        );

        if(assign_leave.getBegin_date().after(assign_leave.getEnd_date())){
            throw new RuntimeException("Start Date must be Before End Date");
        }

        temp.setEmployee(employee);
        temp.setLeave(leave);

        assignLeaveMapper.updateEntityFromDto(assign_leave , temp);
        assignLeaveRepository.save(temp);
    }

}
