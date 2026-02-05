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
public class CompanyResponse {

    @JsonProperty("company_id")
    private UUID companyId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tax_id")
    private String taxId;

    @JsonProperty("active")
    private Boolean active;
}
