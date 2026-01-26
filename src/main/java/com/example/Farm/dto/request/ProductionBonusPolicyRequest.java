package com.example.Farm.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductionBonusPolicyRequest {

    @NotNull
    @Positive
    private Integer workersPerBed;

    @NotNull
    private UUID productionMatrixId;
}