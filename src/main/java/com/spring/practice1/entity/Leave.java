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
    private Long id;

    @Column(name = "leave_type")
    private String type;
}
