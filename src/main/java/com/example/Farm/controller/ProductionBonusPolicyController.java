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
import com.example.Farm.dto.request.ProductionBonusPolicyRequest;
import com.example.Farm.dto.response.ProductionBonusPolicyResponse;
import com.example.Farm.service.ProductionBonusPolicyService;

@RestController
@RequestMapping("/api/production-bonus-policies")
@Tag(name = "Production Bonus Policies", description = "Operations related to production bonus policies")
public class ProductionBonusPolicyController {

    private final ProductionBonusPolicyService productionBonusPolicyService;

    public ProductionBonusPolicyController(ProductionBonusPolicyService productionBonusPolicyService) {
        this.productionBonusPolicyService = productionBonusPolicyService;
    }

    @GetMapping
    @Operation(summary = "Get all production bonus policies")
    @ApiResponse(responseCode = "200", description = "Policies retrieved successfully")
    public List<ProductionBonusPolicyResponse> getAll() {
        return productionBonusPolicyService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get production bonus policy by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Policy found"),
        @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    public ProductionBonusPolicyResponse getById(@PathVariable UUID id) {
        return productionBonusPolicyService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new production bonus policy")
    @ApiResponse(responseCode = "201", description = "Policy created successfully")
    public ResponseEntity<ProductionBonusPolicyResponse> create(
            @Valid @RequestBody ProductionBonusPolicyRequest request) {
        ProductionBonusPolicyResponse created = productionBonusPolicyService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing production bonus policy")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Policy updated"),
        @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    public ProductionBonusPolicyResponse update(@PathVariable UUID id,
                                                @Valid @RequestBody ProductionBonusPolicyRequest request) {
        return productionBonusPolicyService.update(id, request);
    }
}
