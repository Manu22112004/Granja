package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.ProductionBonusPolicyRequest;
import com.example.Farm.dto.response.ProductionBonusPolicyResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.ProductionBonusPolicyMapper;
import com.example.Farm.model.ProductionBonusPolicy;
import com.example.Farm.model.ProductionMatrix;
import com.example.Farm.repository.ProductionBonusPolicyRepository;
import com.example.Farm.repository.ProductionMatrixRepository;

@Service
@Transactional
public class ProductionBonusPolicyService {

    private final ProductionBonusPolicyRepository bonusPolicyRepository;
    private final ProductionMatrixRepository productionMatrixRepository;

    public ProductionBonusPolicyService(ProductionBonusPolicyRepository bonusPolicyRepository,
                                        ProductionMatrixRepository productionMatrixRepository) {
        this.bonusPolicyRepository = bonusPolicyRepository;
        this.productionMatrixRepository = productionMatrixRepository;
    }

    public List<ProductionBonusPolicyResponse> getAll() {
        return ProductionBonusPolicyMapper.toResponseList(bonusPolicyRepository.findAll());
    }

    public ProductionBonusPolicyResponse getById(UUID id) {
        return ProductionBonusPolicyMapper.toResponse(findPolicyOrThrow(id));
    }

    public ProductionBonusPolicyResponse create(ProductionBonusPolicyRequest req) {
        ProductionBonusPolicy policy = ProductionBonusPolicyMapper.toEntity(req);
        ProductionMatrix matrix = findMatrixOrThrow(req.getProductionMatrixId());

        if (matrix.getBonusPolicy() != null) {
            throw new IllegalStateException("ProductionMatrix already has a bonus policy");
        }

        policy.setProductionMatrix(matrix);
        matrix.setBonusPolicy(policy);

        return ProductionBonusPolicyMapper.toResponse(policy);
    }

    public ProductionBonusPolicyResponse update(UUID id, ProductionBonusPolicyRequest req) {
        ProductionBonusPolicy policy = findPolicyOrThrow(id);
        ProductionBonusPolicyMapper.copyToEntity(req, policy);

        if (req.getProductionMatrixId() != null &&
            !policy.getProductionMatrix().getProductionMatrixId().equals(req.getProductionMatrixId())) {

            ProductionMatrix matrix = findMatrixOrThrow(req.getProductionMatrixId());

            if (matrix.getBonusPolicy() != null) {
                throw new IllegalStateException("ProductionMatrix already has a bonus policy");
            }

            policy.getProductionMatrix().setBonusPolicy(null);
            policy.setProductionMatrix(matrix);
            matrix.setBonusPolicy(policy);
        }

        return ProductionBonusPolicyMapper.toResponse(policy);
    }

    private ProductionBonusPolicy findPolicyOrThrow(UUID id) {
        return bonusPolicyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionBonusPolicy", "id", id));
    }

    private ProductionMatrix findMatrixOrThrow(UUID id) {
        return productionMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionMatrix", "id", id));
    }
}
