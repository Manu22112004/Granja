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
@Table(name = "report_organization_infos")
public class ReportOrganizationInfo {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "report_organization_info_id", nullable = false, updatable = false)
    private UUID reportOrganizationInfoId;

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "customer_name", nullable = false, length = 150)
    private String customerName;

    @OneToOne
    @JoinColumn(name = "production_report_id", nullable = false, unique = true)
    private ProductionReport productionReport;
}
