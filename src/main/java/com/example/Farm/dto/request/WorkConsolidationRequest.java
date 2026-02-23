package com.example.Farm.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class WorkConsolidationRequest {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDate;

    @NotNull
    private String pullType;

    @NotNull
    private BigDecimal maxTime;

    @NotNull
    @PositiveOrZero
    private BigDecimal totalHours;

    @NotNull
    @PositiveOrZero
    private Integer totalBedsPlanned;

    @NotNull
    @PositiveOrZero
    private BigDecimal totalBedsProduced;

    @NotNull
    @PositiveOrZero
    private BigDecimal totalCost;

    @NotNull
    private UUID companyId;

    @NotNull
    private UUID customerId;

    private UUID productionId;
    private UUID productionMatrixId;
    private UUID pricingPolicyId;
    private UUID productionReportId;
    private UUID crewLeaderId;
    private UUID qualityCheckerId;
}
