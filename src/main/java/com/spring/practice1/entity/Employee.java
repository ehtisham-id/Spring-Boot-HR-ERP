package com.spring.practice1.entity;

import com.spring.practice1.modules.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity()
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private Date dob ;

    @Column(name = "joining_date")
    private Date joining_date ;

    @Column(name = "salary")
    private double salary = 0;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @JoinColumn(name="approver")
    private Employee approver;
}
