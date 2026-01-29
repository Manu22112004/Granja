package com.example.Farm.dto.response;

import java.util.List;
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

    @JsonProperty("production_matrix_id")
    private UUID productionMatrixId;

    @JsonProperty("max_time")
    private Integer maxTime;

    @JsonProperty("farm_id")
    private UUID farmId;

    private UUID productionBonusPolicyId;
    
    private List<UUID> plannedBedIds;
}
