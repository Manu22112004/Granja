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
public class ReportOrganizationInfoResponse {

    @JsonProperty("report_organization_info_id")
    private UUID reportOrganizationInfoId;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("production_report_id")
    private UUID productionReportId;
}
