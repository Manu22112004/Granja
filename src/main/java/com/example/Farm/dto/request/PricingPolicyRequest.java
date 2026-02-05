package com.example.Farm.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PricingPolicyRequest {

    @NotNull
    @Positive
    private BigDecimal pricePerHour;

    @NotNull
    @Positive
    private BigDecimal pricePerBed;

    @NotNull
    private LocalDate effectiveFrom;

    @NotNull
    private Boolean active;
    
    @NotNull
    private UUID workConsolidationId;
}
