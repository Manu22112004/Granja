package com.example.Farm.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FarmRequest {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal totalAcres;

    @NotNull
    private Integer totalBeds;

    @NotNull
    private Boolean active;
}