package com.example.Farm.model;

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
@Table(name = "worker_productions")
public class WorkerProduction {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "worker_production_id", nullable = false, updatable = false)
    private UUID workerProductionId;

    @Column(name = "beds_assigned", nullable = false)
    private Double bedsAssigned;

    @Column(name = "bonus_applied", nullable = false)
    private Boolean bonusApplied;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "worker_id", nullable = true)
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "production_id", nullable = true)
    private Production production;
}
