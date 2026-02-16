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
public class PricingPolicyResponse {

    @JsonProperty("pricing_policy_id")
    private UUID pricingPolicyId;

    @JsonProperty("price_per_hour")
    private BigDecimal pricePerHour;

    @JsonProperty("price_per_bed")
    private BigDecimal pricePerBed;

    @JsonProperty("effective_from")
    private LocalDate effectiveFrom;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("work_consolidation_id")
    private UUID workConsolidationId;
}
