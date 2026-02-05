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
public class CustomerResponse {

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("contact_info")
    private String contactInfo;

    @JsonProperty("active")
    private Boolean active;
}
