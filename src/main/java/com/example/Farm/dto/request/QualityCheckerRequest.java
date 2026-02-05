package com.example.Farm.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QualityCheckerRequest {

    @NotBlank
    private String certificationLevel;
}
