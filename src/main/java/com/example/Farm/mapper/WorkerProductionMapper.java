package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.WorkerProductionRequest;
import com.example.Farm.dto.response.WorkerProductionResponse;
import com.example.Farm.model.WorkerProduction;

public final class WorkerProductionMapper {

    private WorkerProductionMapper() {}

    public static WorkerProductionResponse toResponse(WorkerProduction wp) {
        if (wp == null) return null;

        return WorkerProductionResponse.builder()
                .workerProductionId(wp.getWorkerProductionId())
                .bedsAssigned(wp.getBedsAssigned())
                .bonusAssigned(wp.getBonusAssigned())
                .totalBeds(wp.getTotalBeds())
                .workerId(
                    wp.getWorker() != null
                            ? wp.getWorker().getPersonId()
                            : null
                )
                .productionId(
                        wp.getProduction() != null
                                ? wp.getProduction().getProductionId()
                                : null
                )
                .build();
    }

    public static List<WorkerProductionResponse> toResponseList(List<WorkerProduction> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(WorkerProductionMapper::toResponse).toList();
    }

    public static WorkerProduction toEntity(WorkerProductionRequest request) {
        if (request == null) return null;
        WorkerProduction wp = new WorkerProduction();
        apply(request, wp);
        return wp;
    }

    public static void copyToEntity(WorkerProductionRequest request, WorkerProduction entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(WorkerProductionRequest request, WorkerProduction wp) {
        if (request.getBedsAssigned() != null) wp.setBedsAssigned(request.getBedsAssigned());
        if (request.getBonusAssigned() != null) wp.setBonusAssigned(request.getBonusAssigned());
        if (request.getTotalBeds() != null) wp.setTotalBeds(request.getTotalBeds());
    }
}
