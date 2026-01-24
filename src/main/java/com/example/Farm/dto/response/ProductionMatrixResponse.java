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
public class ProductionMatrixResponse {
    @JsonProperty("farm_id")
    private UUID farmId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("total_acres")
    private Float totalAcres;

    @JsonProperty("total_beds")
    private Integer totalBeds;

    @JsonProperty("active")
    private Boolean active;
}
