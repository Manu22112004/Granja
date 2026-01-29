package com.example.Farm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Farm.dto.request.ProductionMatrixRequest;
import com.example.Farm.dto.response.ProductionMatrixResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.ProductionMatrixMapper;
import com.example.Farm.model.Farm;
import com.example.Farm.model.ProductionMatrix;
import com.example.Farm.repository.FarmRepository;
import com.example.Farm.repository.ProductionMatrixRepository;

@Service
@Transactional
public class ProductionMatrixService {

    private final ProductionMatrixRepository productionMatrixRepository;
    private final FarmRepository farmRepository;

    public ProductionMatrixService(ProductionMatrixRepository productionMatrixRepository,
                                   FarmRepository farmRepository) {
        this.productionMatrixRepository = productionMatrixRepository;
        this.farmRepository = farmRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductionMatrixResponse> getAll() {
        return productionMatrixRepository.findAll()
                .stream()
                .map(ProductionMatrixMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductionMatrixResponse getById(UUID id) {
        return ProductionMatrixMapper.toResponse(findMatrixOrThrow(id));
    }

    public ProductionMatrixResponse create(ProductionMatrixRequest req) {
        ProductionMatrix matrix = ProductionMatrixMapper.toEntity(req);
        Farm farm = findFarmOrThrow(req.getFarmId());

        if (farm.getProductionMatrix() != null) {
            throw new IllegalStateException("Farm already has a ProductionMatrix");
        }

        matrix.setFarm(farm);
        productionMatrixRepository.save(matrix);
        return ProductionMatrixMapper.toResponse(matrix);
    }

    public ProductionMatrixResponse update(UUID id, ProductionMatrixRequest req) {
        ProductionMatrix matrix = findMatrixOrThrow(id);
        ProductionMatrixMapper.copyToEntity(req, matrix);

        if (req.getFarmId() != null &&
            !matrix.getFarm().getFarmId().equals(req.getFarmId())) {

            Farm newFarm = findFarmOrThrow(req.getFarmId());

            if (newFarm.getProductionMatrix() != null) {
                throw new IllegalStateException("Farm already has a ProductionMatrix");
            }

            matrix.getFarm().setProductionMatrix(null);
            matrix.setFarm(newFarm);
        }

        return ProductionMatrixMapper.toResponse(matrix);
    }

    private ProductionMatrix findMatrixOrThrow(UUID id) {
        return productionMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionMatrix", "id", id));
    }

    private Farm findFarmOrThrow(UUID id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", id));
    }
}
