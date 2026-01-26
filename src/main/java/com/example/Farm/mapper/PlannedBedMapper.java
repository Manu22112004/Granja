package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.PlannedBedRequest;
import com.example.Farm.dto.response.PlannedBedResponse;
import com.example.Farm.model.PlannedBed;

public final class PlannedBedMapper {

    private PlannedBedMapper() {}

    public static PlannedBedResponse toResponse(PlannedBed bed) {
        if (bed == null) return null;

        return PlannedBedResponse.builder()
                .plannedBedId(bed.getPlannedBedId())
                .bedNumber(bed.getBedNumber())
                .area(bed.getArea())
                .build();
    }

    public static List<PlannedBedResponse> toResponseList(List<PlannedBed> beds) {
        if (beds == null || beds.isEmpty()) return List.of();
        return beds.stream().map(PlannedBedMapper::toResponse).toList();
    }

    public static PlannedBed toEntity(PlannedBedRequest request) {
        if (request == null) return null;
        PlannedBed bed = new PlannedBed();
        apply(request, bed);
        return bed;
    }

    public static void copyToEntity(PlannedBedRequest request, PlannedBed entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(PlannedBedRequest request, PlannedBed bed) {
        bed.setBedNumber(request.getBedNumber());
        bed.setArea(request.getArea());
    }
}
