package com.example.Farm.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import lombok.Data;

@Data
public class WorkerProductionRequest {

    @NotNull
    @PositiveOrZero
    private Double bedsAssigned;

    @NotNull
    private Boolean bonusApplied;

    @NotNull
    private UUID workerId;

    @NotNull
    private UUID productionId;
}
