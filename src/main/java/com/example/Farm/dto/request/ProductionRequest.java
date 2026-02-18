package com.example.Farm.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import lombok.Data;

@Data
public class ProductionRequest {

    @NotNull
    @PositiveOrZero
    private Double totalBedsProduced;

    @NotNull
    private UUID workConsolidationId;

    
}
