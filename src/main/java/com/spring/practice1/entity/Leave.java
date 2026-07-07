package com.spring.practice1.entity;

import com.spring.practice1.enums.Reason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leaves")
public class Leave {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private Reason reason;

    @Column(name="leave_date")
    private Date leave_date;

//    @ManyToOne
//    @JoinColumn(name = "employee_id", referencedColumnName = "id")
//    private Employee employee;
}
