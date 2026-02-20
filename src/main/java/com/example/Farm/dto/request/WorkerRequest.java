package com.example.Farm.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WorkerRequest extends PersonRequest {

    @NotBlank
    private String employeeNumber;

    @NotBlank
    private String skillLevel;

    @NotNull
    @Positive
    private BigDecimal hourlyRate;
}
