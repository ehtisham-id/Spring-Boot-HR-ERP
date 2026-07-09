package com.spring.practice1.mapper;

import com.spring.practice1.dto.AssignLeaveRequestDTO;
import com.spring.practice1.dto.AssignLeaveResponseDTO;
import com.spring.practice1.entity.AssignLeave;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignLeaveMapper {
    AssignLeave toEntity(AssignLeaveRequestDTO assign);
    AssignLeaveResponseDTO toDto(AssignLeaveResponseDTO assign);

    List<AssignLeaveResponseDTO> listToDto(List<AssignLeave> assigns);

    void updateEntityFromDto(AssignLeaveRequestDTO leave , @MappingTarget AssignLeave assignLeave);
}
