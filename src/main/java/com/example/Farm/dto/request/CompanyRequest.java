package com.example.Farm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String taxId;

    @NotNull
    private Boolean active;
}
