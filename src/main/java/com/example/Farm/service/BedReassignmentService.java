package com.example.Farm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Farm.model.WorkerProduction;
import com.example.Farm.repository.WorkerProductionRepository;

@Service
@Transactional
public class BedReassignmentService {

    private final WorkerProductionRepository workerProductionRepository;

    public BedReassignmentService(WorkerProductionRepository workerProductionRepository) {
        this.workerProductionRepository = workerProductionRepository;
    }

    public void removeBeds(UUID workerProductionId, Float bedAmount) {
        WorkerProduction wp = workerProductionRepository.findById(workerProductionId)
                .orElseThrow(() -> new IllegalStateException("WorkerProduction not found"));

        wp.setBedsAssigned(wp.getBedsAssigned() - bedAmount);
    }

    public void reassignBeds(Float bedAmount, List<UUID> workerProductionIds) {
        Float perWorker = bedAmount / workerProductionIds.size();

        for (UUID id : workerProductionIds) {
            WorkerProduction wp = workerProductionRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("WorkerProduction not found"));
            wp.setBedsAssigned(wp.getBedsAssigned() + perWorker);
        }
    }

    public void validateTotalBeds(Double expectedTotal, List<WorkerProduction> assignments) {
        Double total = assignments.stream()
                .map(WorkerProduction::getBedsAssigned)
                .reduce(0.0, Double::sum);

        if (!total.equals(expectedTotal)) {
            throw new IllegalStateException("Total beds mismatch");
        }
    }
}
