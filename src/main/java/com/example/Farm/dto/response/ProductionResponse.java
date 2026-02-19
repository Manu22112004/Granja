package com.example.Farm.dto.response;

import java.util.ArrayList;
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
public class ProductionResponse {

    @JsonProperty("production_id")
    private UUID productionId;

    @JsonProperty("total_beds_produced")
    private Double totalBedsProduced;

    @JsonProperty("work_consolidation_id")
    private UUID workConsolidationId;

    @Builder.Default
    private List<UUID> workerProductions = new ArrayList<>();

}
