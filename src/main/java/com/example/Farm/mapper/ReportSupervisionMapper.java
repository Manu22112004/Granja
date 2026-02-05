package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.ReportSupervisionRequest;
import com.example.Farm.dto.response.ReportSupervisionResponse;
import com.example.Farm.model.ReportSupervision;

public final class ReportSupervisionMapper {

    private ReportSupervisionMapper() {}

    public static ReportSupervisionResponse toResponse(ReportSupervision supervision) {
        if (supervision == null) return null;

        return ReportSupervisionResponse.builder()
                .reportSupervisionId(supervision.getReportSupervisionId())
                .crewLeaderName(supervision.getCrewLeaderName())
                .qualityCheckerName(supervision.getQualityCheckerName())
                .productionReportId(
                        supervision.getProductionReport() != null
                                ? supervision.getProductionReport().getProductionReportId()
                                : null
                )
                .build();
    }

    public static List<ReportSupervisionResponse> toResponseList(List<ReportSupervision> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(ReportSupervisionMapper::toResponse).toList();
    }

    public static ReportSupervision toEntity(ReportSupervisionRequest request) {
        if (request == null) return null;
        ReportSupervision supervision = new ReportSupervision();
        apply(request, supervision);
        return supervision;
    }

    public static void copyToEntity(ReportSupervisionRequest request, ReportSupervision entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(ReportSupervisionRequest request, ReportSupervision supervision) {
        if (request.getCrewLeaderName() != null) supervision.setCrewLeaderName(request.getCrewLeaderName());
        if (request.getQualityCheckerName() != null) supervision.setQualityCheckerName(request.getQualityCheckerName());
    }
}
