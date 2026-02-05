package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.ReportOrganizationInfoRequest;
import com.example.Farm.dto.response.ReportOrganizationInfoResponse;
import com.example.Farm.service.ReportOrganizationInfoService;

@RestController
@RequestMapping("/api/report-organization-info")
@Tag(name = "Report Organization Info", description = "Operations related to report organization info")
public class ReportOrganizationInfoController {

    private final ReportOrganizationInfoService reportOrganizationInfoService;

    public ReportOrganizationInfoController(ReportOrganizationInfoService reportOrganizationInfoService) {
        this.reportOrganizationInfoService = reportOrganizationInfoService;
    }

    @GetMapping
    @Operation(summary = "Get all report organization info")
    public List<ReportOrganizationInfoResponse> getAll() {
        return reportOrganizationInfoService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get report organization info by ID")
    public ReportOrganizationInfoResponse getById(@PathVariable UUID id) {
        return reportOrganizationInfoService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create new report organization info")
    public ResponseEntity<ReportOrganizationInfoResponse> create(
            @Valid @RequestBody ReportOrganizationInfoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reportOrganizationInfoService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing report organization info")
    public ResponseEntity<ReportOrganizationInfoResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ReportOrganizationInfoRequest request) {
        return ResponseEntity.ok(reportOrganizationInfoService.update(id, request));
    }
}
