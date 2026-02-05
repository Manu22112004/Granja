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
public class ReportSupervisionResponse {

    @JsonProperty("report_supervision_id")
    private UUID reportSupervisionId;

    @JsonProperty("crew_leader_name")
    private String crewLeaderName;

    @JsonProperty("quality_checker_name")
    private String qualityCheckerName;

    @JsonProperty("production_report_id")
    private UUID productionReportId;
}
