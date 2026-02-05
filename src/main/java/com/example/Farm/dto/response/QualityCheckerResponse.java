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
public class QualityCheckerResponse {

    @JsonProperty("quality_checker_id")
    private UUID qualityCheckerId;

    @JsonProperty("certification_level")
    private String certificationLevel;

    @JsonProperty("person_id")
    private UUID personId;
}
