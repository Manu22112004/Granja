package com.example.Farm.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CrewLeaderRequest {

    @NotBlank
    private String employeeCode;
}
