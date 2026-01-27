package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.FarmRequest;
import com.example.Farm.dto.response.FarmResponse;
import com.example.Farm.service.FarmService;

@RestController
@RequestMapping("/api/farms")
@Tag(name = "Farms", description = "Operations related to farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping
    @Operation(summary = "Get all farms")
    public List<FarmResponse> getAll() {
        return farmService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get farm by ID")
    public FarmResponse getById(@PathVariable UUID id) {
        return farmService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new farm")
    public ResponseEntity<FarmResponse> create(@Valid @RequestBody FarmRequest request) {
        FarmResponse created = farmService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing farm")
    public ResponseEntity<FarmResponse> update(@PathVariable UUID id,
                                               @Valid @RequestBody FarmRequest request) {
        return ResponseEntity.ok(farmService.update(id, request));
    }
}
