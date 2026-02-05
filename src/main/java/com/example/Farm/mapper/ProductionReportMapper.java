package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.ProductionReportRequest;
import com.example.Farm.dto.response.ProductionReportResponse;
import com.example.Farm.model.ProductionReport;

public final class ProductionReportMapper {

    private ProductionReportMapper() {}

    public static ProductionReportResponse toResponse(ProductionReport report) {
        if (report == null) return null;

        return ProductionReportResponse.builder()
                .productionReportId(report.getProductionReportId())
                .reportDate(report.getReportDate())
                .pullType(report.getPullType())
                .numberOfFarms(report.getNumberOfFarms())
                .totalBeds(report.getTotalBeds())
                .workConsolidationId(
                        report.getWorkConsolidation() != null
                                ? report.getWorkConsolidation().getWorkConsolidationId()
                                : null
                )
                .build();
    }

    public static List<ProductionReportResponse> toResponseList(List<ProductionReport> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(ProductionReportMapper::toResponse).toList();
    }

    public static ProductionReport toEntity(ProductionReportRequest request) {
        if (request == null) return null;
        ProductionReport report = new ProductionReport();
        apply(request, report);
        return report;
    }

    public static void copyToEntity(ProductionReportRequest request, ProductionReport entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(ProductionReportRequest request, ProductionReport report) {
        if (request.getReportDate() != null) report.setReportDate(request.getReportDate());
        if (request.getPullType() != null) report.setPullType(request.getPullType());
        if (request.getNumberOfFarms() != null) report.setNumberOfFarms(request.getNumberOfFarms());
        if (request.getTotalBeds() != null) report.setTotalBeds(request.getTotalBeds());
    }
}
