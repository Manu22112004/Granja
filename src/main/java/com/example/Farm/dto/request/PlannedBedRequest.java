package com.example.Farm.dto.request;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PlannedBedRequest {

    @NotNull
    @Positive
    private Integer bedNumber;

    @NotNull
    @Positive
    private BigDecimal area;

    @NotNull
    private UUID productionMatrixId;
}