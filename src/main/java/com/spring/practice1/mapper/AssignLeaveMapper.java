package com.spring.practice1.mapper;

import com.spring.practice1.dto.AssignLeaveRequestDTO;
import com.spring.practice1.dto.AssignLeaveResponseDTO;
import com.spring.practice1.entity.AssignLeave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignLeaveMapper {
    AssignLeave toEntity(AssignLeaveRequestDTO assign);

    @Mapping(target = "leave_type", source = "leave.type")
    AssignLeaveResponseDTO toResponseDTO(AssignLeave assignLeave);

    List<AssignLeaveResponseDTO> listToDto(List<AssignLeave> assigns);

    void updateEntityFromDto(AssignLeaveRequestDTO leave , @MappingTarget AssignLeave assignLeave);
}
