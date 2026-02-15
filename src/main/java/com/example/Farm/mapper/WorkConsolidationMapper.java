package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.WorkConsolidationRequest;
import com.example.Farm.dto.response.WorkConsolidationResponse;
import com.example.Farm.model.WorkConsolidation;

public final class WorkConsolidationMapper {

    private WorkConsolidationMapper() {}

    public static WorkConsolidationResponse toResponse(WorkConsolidation wc) {
        if (wc == null) return null;

        return WorkConsolidationResponse.builder()
                .workConsolidationId(wc.getWorkConsolidationId())
                .workDate(wc.getWorkDate())
                .pullType(wc.getPullType())
                .maxTime(wc.getMaxTime())
                .totalHours(wc.getTotalHours())
                .totalBedsPlanned(wc.getTotalBedsPlanned())
                .totalBedsProduced(wc.getTotalBedsProduced())
                .totalCost(wc.getTotalCost())
                .companyId(wc.getCompany() != null ? wc.getCompany().getCompanyId() : null)
                .customerId(wc.getCustomer() != null ? wc.getCustomer().getCustomerId() : null)
                .productionId(
                    wc.getProduction() != null
                    ? wc.getProduction().getProductionId()
                    : null
                )
                .productionMatrixId(
                        wc.getProductionMatrix() != null
                                ? wc.getProductionMatrix().getProductionMatrixId()
                                : null
                )
                .pricingPolicyId(
                        wc.getPricingPolicy() != null
                                ? wc.getPricingPolicy().getPricingPolicyId()
                                : null
                )
                .productionReportId(
                        wc.getProductionReport() != null
                                ? wc.getProductionReport().getProductionReportId()
                                : null
                )
                .crewLeaderId(
                        wc.getCrewLeader() != null
                                ? wc.getCrewLeader().getPersonId()
                                : null
                )
                .qualityCheckerId(
                        wc.getQualityChecker() != null
                                ? wc.getQualityChecker().getPersonId()
                                : null
                )
                .build();
    }

    public static List<WorkConsolidationResponse> toResponseList(List<WorkConsolidation> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(WorkConsolidationMapper::toResponse).toList();
    }

    public static WorkConsolidation toEntity(WorkConsolidationRequest request) {
        if (request == null) return null;
        WorkConsolidation wc = new WorkConsolidation();
        apply(request, wc);
        return wc;
    }

    public static void copyToEntity(WorkConsolidationRequest request, WorkConsolidation entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(WorkConsolidationRequest request, WorkConsolidation wc) {
        if (request.getWorkDate() != null) wc.setWorkDate(request.getWorkDate());
        if (request.getPullType() != null) wc.setPullType(request.getPullType());
        if (request.getMaxTime() != null) wc.setMaxTime(request.getMaxTime());
        if (request.getTotalHours() != null) wc.setTotalHours(request.getTotalHours());
        if (request.getTotalBedsPlanned() != null) wc.setTotalBedsPlanned(request.getTotalBedsPlanned());
        if (request.getTotalBedsProduced() != null) wc.setTotalBedsProduced(request.getTotalBedsProduced());
        if (request.getTotalCost() != null) wc.setTotalCost(request.getTotalCost());
    }
}
