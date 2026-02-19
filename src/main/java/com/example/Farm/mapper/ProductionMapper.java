package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.ProductionRequest;
import com.example.Farm.dto.response.ProductionResponse;
import com.example.Farm.model.Production;

public final class ProductionMapper {

    private ProductionMapper() {}

    public static ProductionResponse toResponse(Production production) {
        if (production == null) return null;

        return ProductionResponse.builder()
                .productionId(production.getProductionId())
                .totalBedsProduced(production.getTotalBedsProduced())
                .workConsolidationId(production.getWorkConsolidation() != null ? production.getWorkConsolidation().getWorkConsolidationId() : null)
                .workerProductions(
                    production.getWorkerProductions() != null
                        ? production.getWorkerProductions()
                            .stream()
                            .map(wp -> wp.getWorkerProductionId())
                            .toList()
                        : List.of()
                )
                .build();

    }

    public static List<ProductionResponse> toResponseList(List<Production> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(ProductionMapper::toResponse).toList();
    }

    public static Production toEntity(ProductionRequest request) {
        if (request == null) return null;
        Production production = new Production();
        apply(request, production);
        return production;
    }

    public static void copyToEntity(ProductionRequest request, Production entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(ProductionRequest request, Production production) {
        if (request.getTotalBedsProduced() != null) {
            production.setTotalBedsProduced(request.getTotalBedsProduced());
        }
    }
}
