package com.example.Farm.model;

import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "report_supervisions")
public class ReportSupervision {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "report_supervision_id", nullable = false, updatable = false)
    private UUID reportSupervisionId;

    @Column(name = "crew_leader_name", nullable = false, length = 150)
    private String crewLeaderName;

    @Column(name = "quality_checker_name", nullable = false, length = 150)
    private String qualityCheckerName;

    @OneToOne
    @JoinColumn(name = "production_report_id", nullable = false, unique = true)
    private ProductionReport productionReport;
}
