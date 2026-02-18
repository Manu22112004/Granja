package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.ProductionRequest;
import com.example.Farm.dto.response.ProductionResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.ProductionMapper;
import com.example.Farm.model.Production;
import com.example.Farm.model.WorkConsolidation;
import com.example.Farm.repository.ProductionRepository;
import com.example.Farm.repository.WorkConsolidationRepository;

@Service
@Transactional
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final WorkConsolidationRepository workConsolidationRepository;

    public ProductionService(ProductionRepository productionRepository,
                             WorkConsolidationRepository workConsolidationRepository) {
        this.productionRepository = productionRepository;
        this.workConsolidationRepository = workConsolidationRepository;
    }

    public List<ProductionResponse> getAll() {
        return ProductionMapper.toResponseList(productionRepository.findAll());
    }

    public ProductionResponse getById(UUID id) {
        return ProductionMapper.toResponse(findProductionOrThrow(id));
    }

    public ProductionResponse create(ProductionRequest req) {
        Production production = ProductionMapper.toEntity(req);
        WorkConsolidation consolidation = findConsolidationOrThrow(req.getWorkConsolidationId());

        if (consolidation.getProduction() != null) {
            throw new IllegalStateException("WorkConsolidation already has a Production");
        }

        production.setWorkConsolidation(consolidation);
        consolidation.setProduction(production);
        workConsolidationRepository.save(consolidation);
        return ProductionMapper.toResponse(productionRepository.save(production));
    }

    public ProductionResponse update(UUID id, ProductionRequest req) {
        Production production = findProductionOrThrow(id);

        if(req.getWorkConsolidationId() != null &&
           !production.getWorkConsolidation().getWorkConsolidationId().equals(req.getWorkConsolidationId())) {

            WorkConsolidation consolidation = findConsolidationOrThrow(req.getWorkConsolidationId());

            if (consolidation.getProduction() != null) {
                throw new IllegalStateException("WorkConsolidation already has a Production");
            }

            production.getWorkConsolidation().setProduction(null);
            production.setWorkConsolidation(consolidation);
        }

        ProductionMapper.copyToEntity(req, production);
        return ProductionMapper.toResponse(production);
    }

    public ProductionResponse updateTotals(UUID id, Double totalBedsProduced) {
        Production production = findProductionOrThrow(id);
        production.setTotalBedsProduced(totalBedsProduced);
        return ProductionMapper.toResponse(production);
    }

    public ProductionResponse close(UUID id) {
        Production production = findProductionOrThrow(id);
        production.setClosed(true);
        return ProductionMapper.toResponse(production);
    }

    private Production findProductionOrThrow(UUID id) {
        return productionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Production", "id", id));
    }

    private WorkConsolidation findConsolidationOrThrow(UUID id) {
        return workConsolidationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkConsolidation", "id", id));
    }
}
