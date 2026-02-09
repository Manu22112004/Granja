package com.example.Farm.model;

import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "production_reports")
public class ProductionReport {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "production_report_id", nullable = false, updatable = false)
    private UUID productionReportId;

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @Column(name = "pull_type", nullable = false, length = 20)
    private String pullType;

    @Column(name = "number_of_farms", nullable = false)
    private Integer numberOfFarms;

    @Column(name = "total_beds", nullable = false)
    private Double totalBeds;

    @OneToMany(mappedBy = "productionReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEmployeeEntry> employeeEntries;

    @OneToOne(mappedBy = "productionReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private WorkConsolidation workConsolidation;

}
