package com.example.Farm.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CrewLeaderRequest extends PersonRequest {

    @NotBlank
    private String employeeCode;
}
