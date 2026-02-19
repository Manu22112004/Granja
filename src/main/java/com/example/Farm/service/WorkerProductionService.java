package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.WorkerProductionRequest;
import com.example.Farm.dto.response.WorkerProductionResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.WorkerProductionMapper;
import com.example.Farm.model.Production;
import com.example.Farm.model.Worker;
import com.example.Farm.model.WorkerProduction;
import com.example.Farm.repository.ProductionRepository;
import com.example.Farm.repository.WorkerProductionRepository;
import com.example.Farm.repository.WorkerRepository;

@Service
@Transactional
public class WorkerProductionService {

    private final WorkerProductionRepository workerProductionRepository;
    private final WorkerRepository workerRepository;
    private final ProductionRepository productionRepository;

    public WorkerProductionService(WorkerProductionRepository workerProductionRepository,
                                   WorkerRepository workerRepository,
                                   ProductionRepository productionRepository) {
        this.workerProductionRepository = workerProductionRepository;
        this.workerRepository = workerRepository;
        this.productionRepository = productionRepository;
    }

    public List<WorkerProductionResponse> getAll() {
        return WorkerProductionMapper.toResponseList(workerProductionRepository.findAll());
    }

    public WorkerProductionResponse getById(UUID id) {
        return WorkerProductionMapper.toResponse(findWorkerProductionOrThrow(id));
    }

    public WorkerProductionResponse create(WorkerProductionRequest req) {
        WorkerProduction wp = WorkerProductionMapper.toEntity(req);
        Production production = findProductionOrThrow(req.getProductionId());

        if(wp.getProduction() != null) {
            throw new IllegalStateException("WorkerProduction already has a Production");
        }

        production.addWorkerProduction(wp);
        productionRepository.save(production);
        return WorkerProductionMapper.toResponse(wp);

    }

    public WorkerProductionResponse update(UUID id, WorkerProductionRequest req) {
        WorkerProduction wp = findWorkerProductionOrThrow(id);

        if(req.getProductionId() != null &&
           !wp.getProduction().getProductionId().equals(req.getProductionId())) {

            Production production = findProductionOrThrow(req.getProductionId());

            if (production.getWorkerProductions().stream()
                    .anyMatch(existingWp -> !existingWp.getWorkerProductionId().equals(id))) {
                throw new IllegalStateException("Production already has a different WorkerProduction");
            }

            wp.setProduction(production);
            wp.getProduction().getWorkerProductions().add(wp);
        }

        WorkerProductionMapper.copyToEntity(req, wp);
        return WorkerProductionMapper.toResponse(wp);
    }

    private WorkerProduction findWorkerProductionOrThrow(UUID id) {
        return workerProductionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkerProduction", "id", id));
    }

    private Worker findWorkerOrThrow(UUID id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", id));
    }

    private Production findProductionOrThrow(UUID id) {
        return productionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Production", "id", id));
    }
}
