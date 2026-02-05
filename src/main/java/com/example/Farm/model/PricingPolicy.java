package com.example.Farm.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pricing_policies")
public class PricingPolicy {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "pricing_policy_id", nullable = false, updatable = false)
    private UUID pricingPolicyId;

    @Column(name = "price_per_hour", precision = 10, scale = 2, nullable = false)
    private BigDecimal pricePerHour;

    @Column(name = "price_per_bed", precision = 10, scale = 2, nullable = false)
    private BigDecimal pricePerBed;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToOne(mappedBy = "pricingPolicy")
    private WorkConsolidation workConsolidation;
}
