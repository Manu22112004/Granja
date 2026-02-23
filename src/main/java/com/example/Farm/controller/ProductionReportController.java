package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.ProductionReportRequest;
import com.example.Farm.dto.response.ProductionReportResponse;
import com.example.Farm.service.ProductionReportService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/production-reports")
@Tag(name = "Production Reports", description = "Operations related to production reports")
public class ProductionReportController {

    private final ProductionReportService productionReportService;

    public ProductionReportController(ProductionReportService productionReportService) {
        this.productionReportService = productionReportService;
    }

    @GetMapping
    @Operation(summary = "Get all production reports")
    public List<ProductionReportResponse> getAll() {
        return productionReportService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get production report by ID")
    public ProductionReportResponse getById(@PathVariable UUID id) {
        return productionReportService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new production report")
    public ResponseEntity<ProductionReportResponse> create(
            @Valid @RequestBody ProductionReportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productionReportService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing production report")
    public ResponseEntity<ProductionReportResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductionReportRequest request) {
        return ResponseEntity.ok(productionReportService.update(id, request));
    }
}
