package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.WorkerRequest;
import com.example.Farm.dto.response.WorkerResponse;
import com.example.Farm.service.WorkerService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/workers")
@Tag(name = "Workers", description = "Operations related to workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    @Operation(summary = "Get all workers")
    public List<WorkerResponse> getAll() {
        return workerService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get worker by ID")
    public WorkerResponse getById(@PathVariable UUID id) {
        return workerService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new worker")
    public ResponseEntity<WorkerResponse> create(
            @Valid @RequestBody WorkerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workerService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing worker")
    public ResponseEntity<WorkerResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody WorkerRequest request) {
        return ResponseEntity.ok(workerService.update(id, request));
    }
}
