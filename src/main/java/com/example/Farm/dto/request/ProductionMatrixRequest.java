package com.example.Farm.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductionMatrixRequest {
    @NotBlank
    private Byte workersPerBed;

    @NotNull
    private UUID productionMatrixId;
}
