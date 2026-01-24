package com.example.Farm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "production_matrices")
public class ProductionMatrix {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "production_matrix_id", nullable = false, updatable = false)
    private UUID productionMatrixId;

    @Column(name = "max_time", nullable = false)
    private Integer maxTime;

    @OneToMany(mappedBy = "productionMatrix", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlannedBed> plannedBeds;

    @OneToOne(mappedBy = "productionMatrix", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductionBonusPolicy bonusPolicy;

    @OneToOne
    @JoinColumn(name = "farm_id", nullable = false, unique = true)
    private Farm farm;
}
