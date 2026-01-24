package com.example.Farm.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlannedBedRequest {

    @NotBlank
    private String bedNumber;

    @NotBlank
    private Float area;

    @NotNull
    private UUID productionMatrixId;
}