package com.example.Farm.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ReportEmployeeEntryRequest {

    @NotBlank
    private String employeeName;

    @NotBlank
    private String employeeNumber;

    @NotBlank
    private String employeeInitials;

    @NotNull
    @PositiveOrZero
    private Double bedsCompleted;

    @NotNull
    private UUID productionReportId;
}
