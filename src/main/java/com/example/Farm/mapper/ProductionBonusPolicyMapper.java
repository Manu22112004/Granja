package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.ProductionBonusPolicyRequest;
import com.example.Farm.dto.response.ProductionBonusPolicyResponse;
import com.example.Farm.model.ProductionBonusPolicy;

public final class ProductionBonusPolicyMapper {

    private ProductionBonusPolicyMapper() {}

    public static ProductionBonusPolicyResponse toResponse(ProductionBonusPolicy policy) {
        if (policy == null) return null;

        return ProductionBonusPolicyResponse.builder()
                .productionBonusPolicyId(policy.getProductionBonusPolicyId())
                .workersPerBed(policy.getWorkersPerBed())
                .build();
    }

    public static List<ProductionBonusPolicyResponse> toResponseList(List<ProductionBonusPolicy> policies) {
        if (policies == null || policies.isEmpty()) return List.of();
        return policies.stream().map(ProductionBonusPolicyMapper::toResponse).toList();
    }

    public static ProductionBonusPolicy toEntity(ProductionBonusPolicyRequest request) {
        if (request == null) return null;
        ProductionBonusPolicy policy = new ProductionBonusPolicy();
        apply(request, policy);
        return policy;
    }

    public static void copyToEntity(ProductionBonusPolicyRequest request, ProductionBonusPolicy entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(ProductionBonusPolicyRequest request, ProductionBonusPolicy policy) {
        policy.setWorkersPerBed(request.getWorkersPerBed());
    }
}
