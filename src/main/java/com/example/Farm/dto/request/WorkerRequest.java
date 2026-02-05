package com.example.Farm.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WorkerRequest {

    @NotBlank
    private String employeeNumber;

    @NotBlank
    private String skillLevel;

    @NotNull
    @Positive
    private BigDecimal hourlyRate;

    @NotNull
    private Boolean active;
}
