package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.PlannedBedRequest;
import com.example.Farm.dto.response.PlannedBedResponse;
import com.example.Farm.service.PlannedBedService;

@RestController
@RequestMapping("/api/planned-beds")
@Tag(name = "Planned Beds", description = "Operations related to planned beds")
public class PlannedBedController {

    private final PlannedBedService plannedBedService;

    public PlannedBedController(PlannedBedService plannedBedService) {
        this.plannedBedService = plannedBedService;
    }

    @GetMapping
    @Operation(summary = "Get all planned beds")
    @ApiResponse(responseCode = "200", description = "Planned beds retrieved successfully")
    public List<PlannedBedResponse> getAll() {
        return plannedBedService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get planned bed by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Planned bed found"),
        @ApiResponse(responseCode = "404", description = "Planned bed not found")
    })
    public PlannedBedResponse getById(@PathVariable UUID id) {
        return plannedBedService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new planned bed")
    @ApiResponse(responseCode = "201", description = "Planned bed created successfully")
    public ResponseEntity<PlannedBedResponse> create(@Valid @RequestBody PlannedBedRequest request) {
        PlannedBedResponse created = plannedBedService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing planned bed")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Planned bed updated"),
        @ApiResponse(responseCode = "404", description = "Planned bed not found")
    })
    public PlannedBedResponse update(@PathVariable UUID id,
                                     @Valid @RequestBody PlannedBedRequest request) {
        return plannedBedService.update(id, request);
    }
}
