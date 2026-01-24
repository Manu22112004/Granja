package com.example.Farm.model;

import java.math.BigDecimal;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "planned_beds")
public class PlannedBed {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "planned_bed_id", nullable = false,updatable = false)
    private UUID plannedBedId;

    @Column(name = "bed_number", nullable = false)
    private Integer bedNumber;

    @Column(name = "area", precision = 10, scale = 2, nullable = false)
    private BigDecimal area;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "production_matrix_id")
    private ProductionMatrix productionMatrix;
}
