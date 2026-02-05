package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.ReportSupervisionRequest;
import com.example.Farm.dto.response.ReportSupervisionResponse;
import com.example.Farm.service.ReportSupervisionService;

@RestController
@RequestMapping("/api/report-supervisions")
@Tag(name = "Report Supervisions", description = "Operations related to report supervisions")
public class ReportSupervisionController {

    private final ReportSupervisionService reportSupervisionService;

    public ReportSupervisionController(ReportSupervisionService reportSupervisionService) {
        this.reportSupervisionService = reportSupervisionService;
    }

    @GetMapping
    @Operation(summary = "Get all report supervisions")
    public List<ReportSupervisionResponse> getAll() {
        return reportSupervisionService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get report supervision by ID")
    public ReportSupervisionResponse getById(@PathVariable UUID id) {
        return reportSupervisionService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create new report supervision")
    public ResponseEntity<ReportSupervisionResponse> create(
            @Valid @RequestBody ReportSupervisionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reportSupervisionService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing report supervision")
    public ResponseEntity<ReportSupervisionResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ReportSupervisionRequest request) {
        return ResponseEntity.ok(reportSupervisionService.update(id, request));
    }
}
