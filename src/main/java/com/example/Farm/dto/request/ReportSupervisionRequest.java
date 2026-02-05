package com.example.Farm.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportSupervisionRequest {

    @NotBlank
    private String crewLeaderName;

    @NotBlank
    private String qualityCheckerName;

    @NotNull
    private UUID productionReportId;
}
