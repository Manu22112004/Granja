package com.example.Farm.dto.response;

import java.math.BigDecimal;
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
public class WorkConsolidationResponse {

    @JsonProperty("work_consolidation_id")
    private UUID workConsolidationId;

    @JsonProperty("work_date")
    private LocalDate workDate;

    @JsonProperty("pull_type")
    private String pullType;

    @JsonProperty("max_time")
    private BigDecimal maxTime;

    @JsonProperty("total_hours")
    private BigDecimal totalHours;

    @JsonProperty("total_beds_planned")
    private Integer totalBedsPlanned;

    @JsonProperty("total_beds_produced")
    private BigDecimal totalBedsProduced;

    @JsonProperty("total_cost")
    private BigDecimal totalCost;

    @JsonProperty("company_id")
    private UUID companyId;

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("production_id")
    private UUID productionId;

    @JsonProperty("production_matrix_id")
    private UUID productionMatrixId;

    @JsonProperty("pricing_policy_id")
    private UUID pricingPolicyId;

    @JsonProperty("production_report_id")
    private UUID productionReportId;

    @JsonProperty("crew_leader_id")
    private UUID crewLeaderId;

    @JsonProperty("quality_checker_id")
    private UUID qualityCheckerId;
}
