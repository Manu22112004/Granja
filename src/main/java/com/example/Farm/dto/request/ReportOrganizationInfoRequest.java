package com.example.Farm.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportOrganizationInfoRequest {

    @NotBlank
    private String companyName;

    @NotBlank
    private String customerName;

    @NotNull
    private UUID productionReportId;
}
