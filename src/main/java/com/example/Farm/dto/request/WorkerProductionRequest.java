package com.example.Farm.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class WorkerProductionRequest {

    @NotNull
    private Double bedsAssigned;

    @NotNull
    private Boolean bonusApplied;

    private UUID workerId;

    @NotNull
    private UUID productionId;
}
