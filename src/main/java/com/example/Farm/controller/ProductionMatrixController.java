package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.ProductionMatrixRequest;
import com.example.Farm.dto.response.ProductionMatrixResponse;
import com.example.Farm.service.ProductionMatrixService;

@RestController
@RequestMapping("/api/production-matrices")
@Tag(name = "Production Matrices", description = "Operations related to production matrices")
public class ProductionMatrixController {

    private final ProductionMatrixService productionMatrixService;

    public ProductionMatrixController(ProductionMatrixService productionMatrixService) {
        this.productionMatrixService = productionMatrixService;
    }

    @GetMapping
    @Operation(summary = "Get all production matrices")
    public List<ProductionMatrixResponse> getAll() {
        return productionMatrixService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get production matrix by ID")
    public ProductionMatrixResponse getById(@PathVariable UUID id) {
        return productionMatrixService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new production matrix")
    public ResponseEntity<ProductionMatrixResponse> create(
            @Valid @RequestBody ProductionMatrixRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productionMatrixService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing production matrix")
    public ResponseEntity<ProductionMatrixResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductionMatrixRequest request) {
        return ResponseEntity.ok(productionMatrixService.update(id, request));
    }
}
