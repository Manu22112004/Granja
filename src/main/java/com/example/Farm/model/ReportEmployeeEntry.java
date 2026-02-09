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
@Table(name = "report_employee_entries")
public class ReportEmployeeEntry {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "report_employee_entry_id", nullable = false, updatable = false)
    private UUID reportEmployeeEntryId;

    @Column(name = "employee_name", nullable = false, length = 150)
    private String employeeName;

    @Column(name = "employee_number", nullable = false, length = 50)
    private String employeeNumber;

    @Column(name = "employee_initials", nullable = false, length = 10)
    private String employeeInitials;

    @Column(name = "beds_completed", nullable = false)
    private Double bedsCompleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "production_report_id", nullable = false)
    private ProductionReport productionReport;
}
