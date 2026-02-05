package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.WorkerRequest;
import com.example.Farm.dto.response.WorkerResponse;
import com.example.Farm.model.Worker;

public final class WorkerMapper {

    private WorkerMapper() {}

    public static WorkerResponse toResponse(Worker worker) {
        if (worker == null) return null;

        return WorkerResponse.builder()
                .workerId(worker.getPersonId())
                .employeeNumber(worker.getEmployeeNumber())
                .skillLevel(worker.getSkillLevel())
                .hourlyRate(worker.getHourlyRate())
                .active(worker.getActive())
                .build();
    }

    public static List<WorkerResponse> toResponseList(List<Worker> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(WorkerMapper::toResponse).toList();
    }

    public static Worker toEntity(WorkerRequest request) {
        if (request == null) return null;
        Worker worker = new Worker();
        apply(request, worker);
        return worker;
    }

    public static void copyToEntity(WorkerRequest request, Worker entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(WorkerRequest request, Worker worker) {
        if (request.getEmployeeNumber() != null) worker.setEmployeeNumber(request.getEmployeeNumber());
        if (request.getSkillLevel() != null) worker.setSkillLevel(request.getSkillLevel());
        if (request.getHourlyRate() != null) worker.setHourlyRate(request.getHourlyRate());
        if (request.getActive() != null) worker.setActive(request.getActive());
    }
}
