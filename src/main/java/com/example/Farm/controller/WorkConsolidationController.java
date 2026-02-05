package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.WorkConsolidationRequest;
import com.example.Farm.dto.response.WorkConsolidationResponse;
import com.example.Farm.service.WorkConsolidationService;

@RestController
@RequestMapping("/api/work-consolidations")
@Tag(name = "Work Consolidations", description = "Operations related to work consolidations")
public class WorkConsolidationController {

    private final WorkConsolidationService workConsolidationService;

    public WorkConsolidationController(WorkConsolidationService workConsolidationService) {
        this.workConsolidationService = workConsolidationService;
    }

    @GetMapping
    @Operation(summary = "Get all work consolidations")
    public List<WorkConsolidationResponse> getAll() {
        return workConsolidationService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get work consolidation by ID")
    public WorkConsolidationResponse getById(@PathVariable UUID id) {
        return workConsolidationService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new work consolidation")
    public ResponseEntity<WorkConsolidationResponse> create(
            @Valid @RequestBody WorkConsolidationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workConsolidationService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing work consolidation")
    public ResponseEntity<WorkConsolidationResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody WorkConsolidationRequest request) {
        return ResponseEntity.ok(workConsolidationService.update(id, request));
    }
}
