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
public class WorkerProductionResponse {

    @JsonProperty("worker_production_id")
    private UUID workerProductionId;

    @JsonProperty("beds_assigned")
    private Double bedsAssigned;

    @JsonProperty("bonus_assigned")
    private Double bonusAssigned;

    @JsonProperty("total_beds")
    private Double totalBeds;

    @JsonProperty("worker_id")
    private UUID workerId;

    @JsonProperty("production_id")
    private UUID productionId;
}
