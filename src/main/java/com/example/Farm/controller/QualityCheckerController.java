package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.QualityCheckerRequest;
import com.example.Farm.dto.response.QualityCheckerResponse;
import com.example.Farm.service.QualityCheckerService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/quality-checkers")
@Tag(name = "Quality Checkers", description = "Operations related to quality checkers")
public class QualityCheckerController {

    private final QualityCheckerService qualityCheckerService;

    public QualityCheckerController(QualityCheckerService qualityCheckerService) {
        this.qualityCheckerService = qualityCheckerService;
    }

    @GetMapping
    @Operation(summary = "Get all quality checkers")
    public List<QualityCheckerResponse> getAll() {
        return qualityCheckerService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get quality checker by ID")
    public QualityCheckerResponse getById(@PathVariable UUID id) {
        return qualityCheckerService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new quality checker")
    public ResponseEntity<QualityCheckerResponse> create(
            @Valid @RequestBody QualityCheckerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(qualityCheckerService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing quality checker")
    public ResponseEntity<QualityCheckerResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody QualityCheckerRequest request) {
        return ResponseEntity.ok(qualityCheckerService.update(id, request));
    }
}
