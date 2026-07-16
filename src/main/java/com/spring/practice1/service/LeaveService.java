package com.spring.practice1.service;

import com.spring.practice1.dto.LeaveRequestDTO;
import com.spring.practice1.dto.LeaveResponseDTO;
import com.spring.practice1.entity.Leave;
import com.spring.practice1.mapper.LeaveMapper;
import com.spring.practice1.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private LeaveMapper leaveMapper;

    public List<LeaveResponseDTO> getAllLeaves(){
        return           leaveMapper.listToResponse(leaveRepository.findAll());
    }

    public LeaveResponseDTO getLeaveById(Long id){
        return  leaveMapper.toResponse(leaveRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Leave Type Not Found")
        ));
    }

    public void addLeaveType(LeaveRequestDTO leave){
        if(leaveRepository.existsByType(leave.getType())){
            throw new RuntimeException("Already Exist");
        }

        Leave temp = leaveMapper.toEntity(leave);
        leaveRepository.save(temp);
    }

    public void deleteLeaveType(Long id){
        leaveRepository.deleteById(id);
    }

    public  void updateLeaveType(Long id, LeaveRequestDTO leaveDto){
        Leave leave = leaveRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Leave not found")
        );

        if(leaveRepository.existsByType(leaveDto.getType())){
            throw  new RuntimeException("Leave Type already exists");
        }
        leaveMapper.updateEntityFromDto(leaveDto, leave);
        leaveRepository.save(leave);
    }
}
