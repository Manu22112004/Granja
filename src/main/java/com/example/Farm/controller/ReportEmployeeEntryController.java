package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.ReportEmployeeEntryRequest;
import com.example.Farm.dto.response.ReportEmployeeEntryResponse;
import com.example.Farm.service.ReportEmployeeEntryService;

@RestController
@RequestMapping("/api/report-employee-entries")
@Tag(name = "Report Employee Entries", description = "Operations related to report employee entries")
public class ReportEmployeeEntryController {

    private final ReportEmployeeEntryService reportEmployeeEntryService;

    public ReportEmployeeEntryController(ReportEmployeeEntryService reportEmployeeEntryService) {
        this.reportEmployeeEntryService = reportEmployeeEntryService;
    }

    @GetMapping
    @Operation(summary = "Get all report employee entries")
    public List<ReportEmployeeEntryResponse> getAll() {
        return reportEmployeeEntryService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get report employee entry by ID")
    public ReportEmployeeEntryResponse getById(@PathVariable UUID id) {
        return reportEmployeeEntryService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create new report employee entry")
    public ResponseEntity<ReportEmployeeEntryResponse> create(
            @Valid @RequestBody ReportEmployeeEntryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reportEmployeeEntryService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing report employee entry")
    public ResponseEntity<ReportEmployeeEntryResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ReportEmployeeEntryRequest request) {
        return ResponseEntity.ok(reportEmployeeEntryService.update(id, request));
    }
}
