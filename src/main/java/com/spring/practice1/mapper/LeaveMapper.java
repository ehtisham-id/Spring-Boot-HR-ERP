package com.spring.practice1.mapper;

import com.spring.practice1.dto.LeaveRequestDTO;
import com.spring.practice1.dto.LeaveResponseDTO;
import com.spring.practice1.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface LeaveMapper {
    LeaveResponseDTO toResponse(Leave leave);
    Leave toEntity(LeaveRequestDTO leave);

    List<LeaveResponseDTO> listToResponse(List<Leave> leave);

    void updateEntityFromDto(LeaveRequestDTO dto ,@MappingTarget Leave leave);
}
