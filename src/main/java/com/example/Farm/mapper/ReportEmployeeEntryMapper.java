package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.ReportEmployeeEntryRequest;
import com.example.Farm.dto.response.ReportEmployeeEntryResponse;
import com.example.Farm.model.ReportEmployeeEntry;

public final class ReportEmployeeEntryMapper {

    private ReportEmployeeEntryMapper() {}

    public static ReportEmployeeEntryResponse toResponse(ReportEmployeeEntry entry) {
        if (entry == null) return null;

        return ReportEmployeeEntryResponse.builder()
                .reportEmployeeEntryId(entry.getReportEmployeeEntryId())
                .employeeName(entry.getEmployeeName())
                .employeeNumber(entry.getEmployeeNumber())
                .employeeInitials(entry.getEmployeeInitials())
                .bedsCompleted(entry.getBedsCompleted())
                .productionReportId(
                        entry.getProductionReport() != null
                                ? entry.getProductionReport().getProductionReportId()
                                : null
                )
                .build();
    }

    public static List<ReportEmployeeEntryResponse> toResponseList(List<ReportEmployeeEntry> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(ReportEmployeeEntryMapper::toResponse).toList();
    }

    public static ReportEmployeeEntry toEntity(ReportEmployeeEntryRequest request) {
        if (request == null) return null;
        ReportEmployeeEntry entry = new ReportEmployeeEntry();
        apply(request, entry);
        return entry;
    }

    public static void copyToEntity(ReportEmployeeEntryRequest request, ReportEmployeeEntry entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(ReportEmployeeEntryRequest request, ReportEmployeeEntry entry) {
        if (request.getEmployeeName() != null) entry.setEmployeeName(request.getEmployeeName());
        if (request.getEmployeeNumber() != null) entry.setEmployeeNumber(request.getEmployeeNumber());
        if (request.getEmployeeInitials() != null) entry.setEmployeeInitials(request.getEmployeeInitials());
        if (request.getBedsCompleted() != null) entry.setBedsCompleted(request.getBedsCompleted());
    }
}
