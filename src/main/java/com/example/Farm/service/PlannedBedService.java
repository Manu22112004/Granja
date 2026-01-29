package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.PlannedBedRequest;
import com.example.Farm.dto.response.PlannedBedResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.PlannedBedMapper;
import com.example.Farm.model.PlannedBed;
import com.example.Farm.model.ProductionMatrix;
import com.example.Farm.repository.PlannedBedRepository;
import com.example.Farm.repository.ProductionMatrixRepository;

@Service
@Transactional
public class PlannedBedService {

    private final PlannedBedRepository plannedBedRepository;
    private final ProductionMatrixRepository productionMatrixRepository;

    public PlannedBedService(PlannedBedRepository plannedBedRepository,
                             ProductionMatrixRepository productionMatrixRepository) {
        this.plannedBedRepository = plannedBedRepository;
        this.productionMatrixRepository = productionMatrixRepository;
    }

    public List<PlannedBedResponse> getAll() {
        return PlannedBedMapper.toResponseList(plannedBedRepository.findAll());
    }

    public PlannedBedResponse getById(UUID id) {
        return PlannedBedMapper.toResponse(findPlannedBedOrThrow(id));
    }

    @Transactional
    public PlannedBedResponse create(PlannedBedRequest req) {
        PlannedBed bed = PlannedBedMapper.toEntity(req);
        ProductionMatrix matrix = findMatrixOrThrow(req.getProductionMatrixId());

        matrix.addPlannedBed(bed); 

        plannedBedRepository.save(bed); 

        return PlannedBedMapper.toResponse(bed);
    }


    public PlannedBedResponse update(UUID id, PlannedBedRequest req) {
        PlannedBed bed = findPlannedBedOrThrow(id);
        PlannedBedMapper.copyToEntity(req, bed);

        if (req.getProductionMatrixId() != null &&
            !bed.getProductionMatrix().getProductionMatrixId().equals(req.getProductionMatrixId())) {

            ProductionMatrix matrix = findMatrixOrThrow(req.getProductionMatrixId());
            bed.setProductionMatrix(matrix);
        }

        return PlannedBedMapper.toResponse(bed);
    }

    private PlannedBed findPlannedBedOrThrow(UUID id) {
        return plannedBedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlannedBed", "id", id));
    }

    private ProductionMatrix findMatrixOrThrow(UUID id) {
        return productionMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionMatrix", "id", id));
    }
}
