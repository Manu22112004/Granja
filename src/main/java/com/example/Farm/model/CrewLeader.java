package com.example.Farm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "crew_leaders")
public class CrewLeader extends Person {

    @Column(name = "employee_code", nullable = false, unique = true, length = 50)
    private String employeeCode;
}
