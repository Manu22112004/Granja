package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.ReportOrganizationInfoRequest;
import com.example.Farm.dto.response.ReportOrganizationInfoResponse;
import com.example.Farm.model.ReportOrganizationInfo;

public final class ReportOrganizationInfoMapper {

    private ReportOrganizationInfoMapper() {}

    public static ReportOrganizationInfoResponse toResponse(ReportOrganizationInfo info) {
        if (info == null) return null;

        return ReportOrganizationInfoResponse.builder()
                .reportOrganizationInfoId(info.getReportOrganizationInfoId())
                .companyName(info.getCompanyName())
                .customerName(info.getCustomerName())
                .productionReportId(
                        info.getProductionReport() != null
                                ? info.getProductionReport().getProductionReportId()
                                : null
                )
                .build();
    }

    public static List<ReportOrganizationInfoResponse> toResponseList(List<ReportOrganizationInfo> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(ReportOrganizationInfoMapper::toResponse).toList();
    }

    public static ReportOrganizationInfo toEntity(ReportOrganizationInfoRequest request) {
        if (request == null) return null;
        ReportOrganizationInfo info = new ReportOrganizationInfo();
        apply(request, info);
        return info;
    }

    public static void copyToEntity(ReportOrganizationInfoRequest request, ReportOrganizationInfo entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(ReportOrganizationInfoRequest request, ReportOrganizationInfo info) {
        if (request.getCompanyName() != null) info.setCompanyName(request.getCompanyName());
        if (request.getCustomerName() != null) info.setCustomerName(request.getCustomerName());
    }
}
