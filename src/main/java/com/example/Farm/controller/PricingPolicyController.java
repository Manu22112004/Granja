package com.example.Farm.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.example.Farm.dto.request.PricingPolicyRequest;
import com.example.Farm.dto.response.PricingPolicyResponse;
import com.example.Farm.service.PricingPolicyService;

@RestController
@RequestMapping("/api/pricing-policies")
@Tag(name = "Pricing Policies", description = "Operations related to pricing policies")
public class PricingPolicyController {

    private final PricingPolicyService pricingPolicyService;

    public PricingPolicyController(PricingPolicyService pricingPolicyService) {
        this.pricingPolicyService = pricingPolicyService;
    }

    @GetMapping
    @Operation(summary = "Get all pricing policies")
    public List<PricingPolicyResponse> getAll() {
        return pricingPolicyService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pricing policy by ID")
    public PricingPolicyResponse getById(@PathVariable UUID id) {
        return pricingPolicyService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new pricing policy")
    public ResponseEntity<PricingPolicyResponse> create(
            @Valid @RequestBody PricingPolicyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pricingPolicyService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing pricing policy")
    public ResponseEntity<PricingPolicyResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody PricingPolicyRequest request) {
        return ResponseEntity.ok(pricingPolicyService.update(id, request));
    }
}
