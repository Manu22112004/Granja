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
public class CrewLeaderResponse {

    @JsonProperty("crew_leader_id")
    private UUID crewLeaderId;

    @JsonProperty("employee_code")
    private String employeeCode;

    @JsonProperty("person_id")
    private UUID personId;
}
