package com.example.Farm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "work_consolidations")
public class WorkConsolidation {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "work_consolidation_id", nullable = false, updatable = false)
    private UUID workConsolidationId;

    @Column(name = "work_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate workDate;

    @Column(name = "pull_type", nullable = false, length = 20)
    private String pullType;

    @Column(name = "max_time", nullable = false)
    private BigDecimal maxTime;

    @Column(name = "total_hours", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalHours;

    @Column(name = "total_beds_planned", nullable = false)
    private Integer totalBedsPlanned;

    @Column(name = "total_beds_produced", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalBedsProduced;

    @Column(name = "total_cost", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalCost;

    @Column(name = "closed", nullable = false)
    private Boolean closed = false;

    /*-------------------
        RELATIONSHIPS
    -------------------*/

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(optional = true)
    @JoinColumn(name = "production_report_id", nullable = true, unique = true)
    private ProductionReport productionReport;

    @OneToOne(optional = true)
    @JoinColumn(name = "production_id", nullable = true, unique = true)
    private Production production;

    @OneToOne(optional = true)
    @JoinColumn(name = "production_matrix_id", nullable = true, unique = true)
    private ProductionMatrix productionMatrix;

    @OneToOne(optional = true)
    @JoinColumn(name = "pricing_policy_id", nullable = true, unique = true)
    private PricingPolicy pricingPolicy;

    @ManyToOne(optional = true)
    @JoinColumn(name = "crew_leader_id", nullable = true)
    private CrewLeader crewLeader;

    @ManyToOne(optional = true)
    @JoinColumn(name = "quality_checker_id", nullable = true)
    private QualityChecker qualityChecker;

}
