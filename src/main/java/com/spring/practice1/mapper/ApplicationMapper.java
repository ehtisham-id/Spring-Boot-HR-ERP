package com.spring.practice1.mapper;

import com.spring.practice1.dto.ApplicationRequestDTO;
import com.spring.practice1.dto.ApplicationResponseDTO;
import com.spring.practice1.entity.Application;
import com.spring.practice1.enums.ApplicationStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

//Otherwise it will unable to map its DTOs because they contain methods of EmployeeMapper
@Mapper(componentModel = "spring" , uses = {EmployeeMapper.class})
public interface ApplicationMapper {
    Application toEntity(ApplicationRequestDTO application);

    @Mapping(target = "employee_id" ,source = "employee.id")
    @Mapping(target = "employee_name", source = "employee.name")
    @Mapping(target = "leave_type", source = "leave.type")
    ApplicationResponseDTO toResponse(Application application);

    default ApplicationStatus mapStatus(String status) {
        if (status == null) return null;
        return switch (status.toLowerCase()) {
            case "accept" -> ApplicationStatus.ACCEPT;
            case "reject" -> ApplicationStatus.REJECT;
            case "pending" -> ApplicationStatus.PENDING;
            default -> throw new IllegalArgumentException("Unknown status: " + status);
        };
    }

    List<ApplicationResponseDTO> getAll(List<Application> applications);
}
