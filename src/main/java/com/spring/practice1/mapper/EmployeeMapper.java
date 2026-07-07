package com.spring.practice1.mapper;

import com.spring.practice1.dto.EmployeeRequestDTO;
import com.spring.practice1.dto.EmployeeResponseDTO;
import com.spring.practice1.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = LeaveMapper.class)
public interface EmployeeMapper {

    EmployeeResponseDTO toResponse(Employee employee);
    Employee toEntity(EmployeeRequestDTO employee);

    List<EmployeeResponseDTO> listToResponse(List<Employee> employees);
}
