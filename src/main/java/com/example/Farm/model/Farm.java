package com.example.Farm.model;

import java.math.BigDecimal;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "farms")
public class Farm {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "farm_id", nullable = false, updatable = false)
    private UUID farmId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "total_acres", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAcres;

    @Column(name = "total_beds", nullable = false)
    private Integer totalBeds;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToOne(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ProductionMatrix productionMatrix;
}
