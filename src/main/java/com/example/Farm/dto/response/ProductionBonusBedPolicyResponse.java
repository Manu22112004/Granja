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
public class ProductionBonusBedPolicyResponse {
    @JsonProperty("production_bonus_bed_policy_id")
    private UUID productionBonusBedPolicyId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("workers_per_bed")
    private Byte workersPerBed;
    
    @JsonProperty("production_matrix_id")
    private ProductionMatrixResponse productionMatrix;
}
