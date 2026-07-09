package com.spring.practice1.entity;

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
@Table(name="assign_leave")
public class AssignLeave {
    @Id
    @GeneratedValue
    private Long Id;

    @ManyToOne(
            targetEntity = Employee.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "employee_id"
    )
    private Employee employee;

    @ManyToOne(
            targetEntity = Leave.class ,
            cascade = CascadeType.ALL ,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "leave_id"
    )
    private  Leave leave;

    @Column(name = "begin_date")
    private Date begin_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "max_leaves")
    private Integer max_leaves = 5;
}
