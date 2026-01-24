package com.example.Farm.model;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "production_bonus_policies")
public class ProductionBonusPolicy {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "production_bonus_policy_id", nullable = false, updatable = false)
    private UUID productionBonusPolicyId;

    @Column(name = "workers_per_bed", nullable = false)
    private Integer workersPerBed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_matrix_id", nullable = false, unique = true)
    private ProductionMatrix productionMatrix;
}
