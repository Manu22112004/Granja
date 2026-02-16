package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.PricingPolicyRequest;
import com.example.Farm.dto.response.PricingPolicyResponse;
import com.example.Farm.model.PricingPolicy;

public final class PricingPolicyMapper {

    private PricingPolicyMapper() {}

    public static PricingPolicyResponse toResponse(PricingPolicy policy) {
        if (policy == null) return null;

        return PricingPolicyResponse.builder()
                .pricingPolicyId(policy.getPricingPolicyId())
                .pricePerHour(policy.getPricePerHour())
                .pricePerBed(policy.getPricePerBed())
                .effectiveFrom(policy.getEffectiveFrom())
                .active(policy.getActive())
                .workConsolidationId(policy.getWorkConsolidation() != null 
                    ? policy.getWorkConsolidation().getWorkConsolidationId() 
                    : null)
                .build();
    }

    public static List<PricingPolicyResponse> toResponseList(List<PricingPolicy> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(PricingPolicyMapper::toResponse).toList();
    }

    public static PricingPolicy toEntity(PricingPolicyRequest request) {
        if (request == null) return null;
        PricingPolicy policy = new PricingPolicy();
        apply(request, policy);
        return policy;
    }

    public static void copyToEntity(PricingPolicyRequest request, PricingPolicy entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(PricingPolicyRequest request, PricingPolicy policy) {
        if (request.getPricePerHour() != null) policy.setPricePerHour(request.getPricePerHour());
        if (request.getPricePerBed() != null) policy.setPricePerBed(request.getPricePerBed());
        if (request.getEffectiveFrom() != null) policy.setEffectiveFrom(request.getEffectiveFrom());
        if (request.getActive() != null) policy.setActive(request.getActive());
    }
}
