package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.ProductionMatrixRequest;
import com.example.Farm.dto.response.ProductionMatrixResponse;
import com.example.Farm.model.ProductionMatrix;

public final class ProductionMatrixMapper {

    private ProductionMatrixMapper() {}

    public static ProductionMatrixResponse toResponse(ProductionMatrix matrix) {
        if (matrix == null) return null;

        return ProductionMatrixResponse.builder()
                .productionMatrixId(matrix.getProductionMatrixId())
                .maxTime(matrix.getMaxTime())
                .farmId(matrix.getFarm() != null ? matrix.getFarm().getFarmId() : null)
                .build();
    }

    public static List<ProductionMatrixResponse> toResponseList(List<ProductionMatrix> matrices) {
        if (matrices == null || matrices.isEmpty()) return List.of();
        return matrices.stream().map(ProductionMatrixMapper::toResponse).toList();
    }

    public static ProductionMatrix toEntity(ProductionMatrixRequest request) {
        if (request == null) return null;
        ProductionMatrix matrix = new ProductionMatrix();
        apply(request, matrix);
        return matrix;
    }

    public static void copyToEntity(ProductionMatrixRequest request, ProductionMatrix entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(ProductionMatrixRequest request, ProductionMatrix matrix) {
        if (request.getMaxTime() != null) {
            matrix.setMaxTime(request.getMaxTime());
        }
    }
}
