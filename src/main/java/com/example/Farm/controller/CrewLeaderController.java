package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.CrewLeaderRequest;
import com.example.Farm.dto.response.CrewLeaderResponse;
import com.example.Farm.service.CrewLeaderService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/crew-leaders")
@Tag(name = "Crew Leaders", description = "Operations related to crew leaders")
public class CrewLeaderController {

    private final CrewLeaderService crewLeaderService;

    public CrewLeaderController(CrewLeaderService crewLeaderService) {
        this.crewLeaderService = crewLeaderService;
    }

    @GetMapping
    @Operation(summary = "Get all crew leaders")
    public List<CrewLeaderResponse> getAll() {
        return crewLeaderService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get crew leader by ID")
    public CrewLeaderResponse getById(@PathVariable UUID id) {
        return crewLeaderService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new crew leader")
    public ResponseEntity<CrewLeaderResponse> create(
            @Valid @RequestBody CrewLeaderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(crewLeaderService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing crew leader")
    public ResponseEntity<CrewLeaderResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody CrewLeaderRequest request) {
        return ResponseEntity.ok(crewLeaderService.update(id, request));
    }
}
