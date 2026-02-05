package com.example.Farm.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class WorkConsolidationRequest {

    @NotNull
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
}
