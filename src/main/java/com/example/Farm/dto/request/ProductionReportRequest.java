package com.example.Farm.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductionReportRequest {

    @Nullable
    private LocalDate reportDate;

    // @NotNull
    // private String pullType;

    // @NotNull
    // @PositiveOrZero
    // private Integer numberOfFarms;

    // @NotNull
    // @PositiveOrZero
    // private Double totalBeds;

    @NotNull
    private UUID workConsolidationId;
}
