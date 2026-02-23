package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.ProductionRequest;
import com.example.Farm.dto.response.ProductionResponse;
import com.example.Farm.service.ProductionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/productions")
@Tag(name = "Productions", description = "Operations related to productions")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    @Operation(summary = "Get all productions")
    public List<ProductionResponse> getAll() {
        return productionService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get production by ID")
    public ProductionResponse getById(@PathVariable UUID id) {
        return productionService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new production")
    public ResponseEntity<ProductionResponse> create(
            @Valid @RequestBody ProductionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productionService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing production")
    public ResponseEntity<ProductionResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductionRequest request) {
        return ResponseEntity.ok(productionService.update(id, request));
    }
}
