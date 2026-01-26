package com.example.Farm.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductionMatrixRequest {

    @NotNull
    private Integer maxTime;

    @NotNull
    private UUID farmId;

}
