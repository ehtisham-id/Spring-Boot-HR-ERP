package com.spring.practice1.repository;

import com.spring.practice1.entity.AssignLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface AssignLeaveRepository extends JpaRepository<AssignLeave, Long> {
    @Query("SELECT a FROM AssignLeave a WHERE a.employee.id = :employeeId " +
            "AND a.leave.id = :leaveId " +
            "AND a.begin_date <= :currentDate " +
            "AND a.end_date >= :currentDate")
    Optional<AssignLeave> findActiveEntitlement(@Param("employeeId") Long employeeId,
                                                @Param("leaveId") Long leaveId,
                                                @Param("currentDate") Date currentDate);
}
