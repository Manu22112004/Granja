package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.WorkerProductionRequest;
import com.example.Farm.dto.response.WorkerProductionResponse;
import com.example.Farm.service.WorkerProductionService;

@RestController
@RequestMapping("/api/worker-productions")
@Tag(name = "Worker Productions", description = "Operations related to worker productions")
public class WorkerProductionController {

    private final WorkerProductionService workerProductionService;

    public WorkerProductionController(WorkerProductionService workerProductionService) {
        this.workerProductionService = workerProductionService;
    }

    @GetMapping
    @Operation(summary = "Get all worker productions")
    public List<WorkerProductionResponse> getAll() {
        return workerProductionService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get worker production by ID")
    public WorkerProductionResponse getById(@PathVariable UUID id) {
        return workerProductionService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new worker production")
    public ResponseEntity<WorkerProductionResponse> create(
            @Valid @RequestBody WorkerProductionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workerProductionService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing worker production")
    public ResponseEntity<WorkerProductionResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody WorkerProductionRequest request) {
        return ResponseEntity.ok(workerProductionService.update(id, request));
    }
}
