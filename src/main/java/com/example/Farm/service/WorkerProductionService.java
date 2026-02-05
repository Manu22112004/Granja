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

    public WorkerProductionResponse assignBeds(WorkerProductionRequest req) {
        WorkerProduction wp = WorkerProductionMapper.toEntity(req);
        Worker worker = findWorkerOrThrow(req.getWorkerId());
        Production production = findProductionOrThrow(req.getProductionId());

        wp.setWorker(worker);
        wp.setProduction(production);

        return WorkerProductionMapper.toResponse(workerProductionRepository.save(wp));
    }

    public WorkerProductionResponse updateBeds(UUID id, Double bedsAssigned) {
        WorkerProduction wp = findWorkerProductionOrThrow(id);
        wp.setBedsAssigned(bedsAssigned);
        return WorkerProductionMapper.toResponse(wp);
    }

    public WorkerProductionResponse create(WorkerProductionRequest req) {
        WorkerProduction wp = WorkerProductionMapper.toEntity(req);
        return WorkerProductionMapper.toResponse(workerProductionRepository.save(wp));
    }

    public WorkerProductionResponse update(UUID id, WorkerProductionRequest req) {
        WorkerProduction wp = findWorkerProductionOrThrow(id);
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
