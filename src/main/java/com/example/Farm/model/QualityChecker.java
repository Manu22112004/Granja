package com.example.Farm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "quality_checkers")
public class QualityChecker extends Person {

    @Column(name = "certification_level", nullable = false, length = 50)
    private String certificationLevel;
}
