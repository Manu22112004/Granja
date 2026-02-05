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
public class ReportEmployeeEntryResponse {

    @JsonProperty("report_employee_entry_id")
    private UUID reportEmployeeEntryId;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("employee_number")
    private String employeeNumber;

    @JsonProperty("employee_initials")
    private String employeeInitials;

    @JsonProperty("beds_completed")
    private Double bedsCompleted;

    @JsonProperty("production_report_id")
    private UUID productionReportId;
}
