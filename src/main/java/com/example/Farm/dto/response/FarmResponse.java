package com.example.Farm.dto.response;

import java.math.BigDecimal;
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
public class FarmResponse {
    
    @JsonProperty("farm_id")
    private UUID farmId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("total_acres")
    private BigDecimal totalAcres;

    @JsonProperty("total_beds")
    private Integer totalBeds;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("production_matrix_id")
    private UUID productionMatrix;
}
