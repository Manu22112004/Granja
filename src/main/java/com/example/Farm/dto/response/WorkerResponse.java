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
public class WorkerResponse {

    @JsonProperty("worker_id")
    private UUID workerId;

    @JsonProperty("employee_number")
    private String employeeNumber;

    @JsonProperty("skill_level")
    private String skillLevel;

    @JsonProperty("hourly_rate")
    private BigDecimal hourlyRate;

    @JsonProperty("active")
    private Boolean active;
}
