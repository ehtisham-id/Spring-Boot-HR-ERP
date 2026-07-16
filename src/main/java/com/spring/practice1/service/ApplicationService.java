package com.spring.practice1.service;

import com.spring.practice1.dto.ApplicationRequestDTO;
import com.spring.practice1.dto.ApplicationResponseDTO;
import com.spring.practice1.dto.ChangeApplicationStatusDTO;
import com.spring.practice1.entity.Application;
import com.spring.practice1.entity.AssignLeave;
import com.spring.practice1.entity.Employee;
import com.spring.practice1.entity.Leave;
import com.spring.practice1.enums.ApplicationStatus;
import com.spring.practice1.mapper.ApplicationMapper;
import com.spring.practice1.repository.ApplicationRepository;
import com.spring.practice1.repository.AssignLeaveRepository;
import com.spring.practice1.repository.EmployeeRepository;
import com.spring.practice1.repository.LeaveRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private AssignLeaveRepository assignLeaveRepository;

    public List<ApplicationResponseDTO> getAll(){
        return applicationMapper.getAll(applicationRepository.findAll());
    }

    public ApplicationResponseDTO geApplicationById(Long id){
        return applicationMapper.toResponse(applicationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User Not Found")
        ));
    }


    public void addApplication(@NonNull ApplicationRequestDTO dto) {
        Employee emp = employeeRepository.findById(dto.getEmployee_id())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Leave leave = leaveRepository.findById(dto.getLeave_id())
                .orElseThrow(() -> new RuntimeException("Leave type not found"));

        LocalDate today = LocalDate.now();
        LocalDate start = dto.getBegin_date().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = dto.getEnd_date().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        if (!start.isAfter(today)) {
            throw new RuntimeException("Leave must start after today");
        }
        if (start.isAfter(end)) {
            throw new RuntimeException("Start date must be before or equal to end date");
        }

        AssignLeave entitlement = assignLeaveRepository
                .findActiveEntitlement(emp.getId(), leave.getId(), Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .orElseThrow(() -> new RuntimeException("Employee not assigned this leave type or period"));

        if (start.isBefore(entitlement.getBegin_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) ||
                end.isAfter(entitlement.getEnd_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
            throw new RuntimeException("Leave dates must be within the assigned period");
        }

        if(applicationRepository.hasOverlappingLeave(emp.getId(), dto.getBegin_date(), dto.getEnd_date())){
            throw  new RuntimeException("Your application for leave already exist in this duration");
        }

        Long usedDays = applicationRepository.countApprovedLeavesInPeriod(
                emp.getId(),
                leave.getId(),
                ApplicationStatus.PENDING,
                entitlement.getBegin_date(),
                entitlement.getEnd_date()
        );
        long used = (usedDays == null) ? 0L : usedDays;

        long requestedDays = ChronoUnit.DAYS.between(start, end) + 1;
        long remaining = entitlement.getMax_leaves() - requestedDays - usedDays;

        if (remaining<0) {
            throw new RuntimeException("Insufficient balance: requested " + (requestedDays+usedDays) +
                    ", available " + entitlement.getMax_leaves());
        }

        entitlement.setMax_leaves((int) remaining);
        Application application = applicationMapper.toEntity(dto);
        application.setEmployee(emp);
        application.setLeave(leave);
        application.setStatus(ApplicationStatus.PENDING);
        applicationRepository.save(application);
        assignLeaveRepository.save(entitlement);
    }

    public void deleteApplication(Long id){
        applicationRepository.deleteById(id);
    }

    public void changeApplicationStatus(Long id, ChangeApplicationStatusDTO dto) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getEmployee().getApprover().getId().equals(dto.getApprover_id())) {
            throw new RuntimeException("You cannot approve or reject this leave application");
        }

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new RuntimeException("Application has already been processed");
        }

        ApplicationStatus targetStatus = applicationMapper.mapStatus(dto.getStatus());

        if (targetStatus == ApplicationStatus.REJECT) {
            LocalDate start = application.getBegin_date().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = application.getEnd_date().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            long requestedDays = ChronoUnit.DAYS.between(start, end) + 1;

            AssignLeave entitlement = assignLeaveRepository
                    .findActiveEntitlement(
                            application.getEmployee().getId(),
                            application.getLeave().getId(),
                            Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
                    )
                    .orElseThrow(() -> new RuntimeException("Active leave entitlement record not found"));


            int updatedMaxLeaves = entitlement.getMax_leaves() + (int) requestedDays;
            entitlement.setMax_leaves(updatedMaxLeaves);

            assignLeaveRepository.save(entitlement);
        }

        application.setStatus(targetStatus);
        application.setReason(dto.getReason());
        applicationRepository.save(application);
    }


}
