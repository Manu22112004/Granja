package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.Farm.dto.request.ProductionMatrixRequest;
import com.example.Farm.dto.response.ProductionMatrixResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.ProductionMatrixMapper;
import com.example.Farm.model.Farm;
import com.example.Farm.model.ProductionMatrix;
import com.example.Farm.repository.FarmRepository;
import com.example.Farm.repository.ProductionMatrixRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductionMatrixService {

    private final ProductionMatrixRepository productionMatrixRepository;
    private final FarmRepository farmRepository;

    public ProductionMatrixService(ProductionMatrixRepository productionMatrixRepository,
                                   FarmRepository farmRepository) {
        this.productionMatrixRepository = productionMatrixRepository;
        this.farmRepository = farmRepository;
    }

    public List<ProductionMatrixResponse> getAll() {
        return ProductionMatrixMapper.toResponseList(productionMatrixRepository.findAll());
    }

    public ProductionMatrixResponse getById(UUID id) {
        return ProductionMatrixMapper.toResponse(findMatrixOrThrow(id));
    }

    @Transactional
    public ProductionMatrixResponse create(ProductionMatrixRequest req) {
        ProductionMatrix matrix = ProductionMatrixMapper.toEntity(req);
        Farm farm = findFarmOrThrow(req.getFarmId());

        matrix.setFarm(farm);
        farm.setProductionMatrix(matrix);

        return ProductionMatrixMapper.toResponse(productionMatrixRepository.save(matrix));
    }

    @Transactional
    public ProductionMatrixResponse update(UUID id, ProductionMatrixRequest req) {
        ProductionMatrix matrix = findMatrixOrThrow(id);
        ProductionMatrixMapper.copyToEntity(req, matrix);
        return ProductionMatrixMapper.toResponse(matrix);
    }

    // ---------- Helpers ----------

    private ProductionMatrix findMatrixOrThrow(UUID id) {
        return productionMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionMatrix", "id", id));
    }

    private Farm findFarmOrThrow(UUID id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", id));
    }
}
