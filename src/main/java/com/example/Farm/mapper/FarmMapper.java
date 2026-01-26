package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.FarmRequest;
import com.example.Farm.dto.response.FarmResponse;
import com.example.Farm.model.Farm;

public final class FarmMapper {

    private FarmMapper() {}

    public static FarmResponse toResponse(Farm farm) {
        if (farm == null) return null;

        return FarmResponse.builder()
                .farmId(farm.getFarmId())
                .name(farm.getName())
                .totalAcres(farm.getTotalAcres())
                .totalBeds(farm.getTotalBeds())
                .active(farm.getActive())
                .build();
    }

    public static List<FarmResponse> toResponseList(List<Farm> farms) {
        if (farms == null || farms.isEmpty()) return List.of();
        return farms.stream().map(FarmMapper::toResponse).toList();
    }

    public static Farm toEntity(FarmRequest request) {
        if (request == null) return null;
        Farm farm = new Farm();
        apply(request, farm);
        return farm;
    }

    public static void copyToEntity(FarmRequest request, Farm entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(FarmRequest request, Farm farm) {
        farm.setName(request.getName());
        farm.setTotalAcres(request.getTotalAcres());
        farm.setTotalBeds(request.getTotalBeds());
        farm.setActive(request.getActive());
    }
}
