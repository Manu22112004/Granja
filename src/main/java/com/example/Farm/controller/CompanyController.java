package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.CompanyRequest;
import com.example.Farm.dto.response.CompanyResponse;
import com.example.Farm.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Companies", description = "Operations related to companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    @Operation(summary = "Get all companies")
    public List<CompanyResponse> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company by ID")
    public CompanyResponse getById(@PathVariable UUID id) {
        return companyService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new company")
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing company")
    public ResponseEntity<CompanyResponse> update(@PathVariable UUID id,
                                                  @Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.ok(companyService.update(id, request));
    }
}
