package com.example.Farm.dto.response;

import java.time.LocalDate;
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
public class ProductionReportResponse {

    @JsonProperty("production_report_id")
    private UUID productionReportId;

    @JsonProperty("report_date")
    private LocalDate reportDate;

    @JsonProperty("pull_type")
    private String pullType;

    @JsonProperty("number_of_farms")
    private Integer numberOfFarms;

    @JsonProperty("total_beds")
    private Double totalBeds;

    @JsonProperty("work_consolidation_id")
    private UUID workConsolidationId;
}
