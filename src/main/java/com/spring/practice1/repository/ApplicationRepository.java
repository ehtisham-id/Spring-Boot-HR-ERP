package com.spring.practice1.repository;

import com.spring.practice1.entity.Application;
import com.spring.practice1.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ApplicationRepository extends JpaRepository<Application , Long> {
    @Query(value = "SELECT COALESCE(SUM(EXTRACT(DAY FROM (end_date - begin_date)) + 1), 0) " +
            "FROM application a " +
            "WHERE a.employee = :employeeId AND a.leave = :leaveId " +
            "AND a.status = :status AND a.begin_date >= :startDate AND a.end_date <= :endDate",
            nativeQuery = true)
    Long countApprovedLeavesInPeriod(@Param("employeeId") Long employeeId,
                                     @Param("leaveId") Long leaveId,
                                     @Param("status") ApplicationStatus status,
                                     @Param("startDate") Date startDate,
                                     @Param("endDate") Date endDate);


    @Query("SELECT COUNT(a) > 0 FROM Application a " +
            "WHERE a.employee.id = :employeeId " +
            "AND a.begin_date <= :endDate " +
            "AND a.end_date >= :startDate")
    boolean hasOverlappingLeave(@Param("employeeId") Long employeeId,
                                @Param("startDate") Date startDate,
                                @Param("endDate") Date endDate);
}
