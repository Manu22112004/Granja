package com.example.Farm.model;

import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "productions")
public class Production {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "production_id", nullable = false, updatable = false)
    private UUID productionId;

    @Column(name = "total_beds_produced", nullable = false)
    private Double totalBedsProduced;

    @Column(name = "closed", nullable = false)
    private Boolean closed = false;

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkerProduction> workerProductions;

}
