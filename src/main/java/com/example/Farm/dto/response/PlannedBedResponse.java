package com.example.Farm.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlannedBedResponse {
    @JsonProperty("planned_bed_id")
    private UUID plannedBedId;

    @JsonProperty("bed_number")
    private Integer bedNumber;

    @JsonProperty("area")
    private Float area;

    @JsonProperty("production_matrix_id")
    private ProductionMatrixResponse productionMatrix;
}

