package com.spring.practice1.entity;

import com.spring.practice1.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Employee.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne(targetEntity = Leave.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "leave")
    private Leave leave;

    @Column(name="begin_date")
    private Date begin_date;

    @Column(name="end_date")
    private Date end_date;

    @Column(name="status")
    private ApplicationStatus status = ApplicationStatus.PENDING;

}
