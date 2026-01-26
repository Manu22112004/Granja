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
    @ApiResponse(responseCode = "200", description = "Matrices retrieved successfully")
    public List<ProductionMatrixResponse> getAll() {
        return productionMatrixService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get production matrix by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Matrix found"),
        @ApiResponse(responseCode = "404", description = "Matrix not found")
    })
    public ProductionMatrixResponse getById(@PathVariable UUID id) {
        return productionMatrixService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new production matrix")
    @ApiResponse(responseCode = "201", description = "Matrix created successfully")
    public ResponseEntity<ProductionMatrixResponse> create(@Valid @RequestBody ProductionMatrixRequest request) {
        ProductionMatrixResponse created = productionMatrixService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing production matrix")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Matrix updated"),
        @ApiResponse(responseCode = "404", description = "Matrix not found")
    })
    public ProductionMatrixResponse update(@PathVariable UUID id,
                                           @Valid @RequestBody ProductionMatrixRequest request) {
        return productionMatrixService.update(id, request);
    }
}
