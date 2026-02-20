package com.example.Farm.dto.response;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerResponse extends PersonResponse {

    @JsonProperty("employee_number")
    private String employeeNumber;

    @JsonProperty("skill_level")
    private String skillLevel;

    @JsonProperty("hourly_rate")
    private BigDecimal hourlyRate;

}
