package com.example.Farm.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FarmRequest {

    @NotBlank
    private String name;

    @NotBlank
    private Float totalAcrees;

    @NotBlank
    private Integer totalBeds;

    @NotBlank
    private Boolean active;

    @NotNull
    private UUID productionMatrixId;
}