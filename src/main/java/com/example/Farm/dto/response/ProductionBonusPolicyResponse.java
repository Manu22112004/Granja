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
public class ProductionBonusPolicyResponse {

    @JsonProperty("production_bonus_policy_id")
    private UUID productionBonusPolicyId;

    @JsonProperty("workers_per_bed")
    private Integer workersPerBed;
    
    @JsonProperty("production_matrix_id")
    private UUID productionMatrixId;
}
