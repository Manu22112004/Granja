package com.example.Farm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Farm.dto.request.PricingPolicyRequest;
import com.example.Farm.dto.response.PricingPolicyResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.PricingPolicyMapper;
import com.example.Farm.model.PricingPolicy;
import com.example.Farm.model.WorkConsolidation;
import com.example.Farm.repository.PricingPolicyRepository;
import com.example.Farm.repository.WorkConsolidationRepository;

@Service
@Transactional
public class PricingPolicyService {

    private final PricingPolicyRepository pricingPolicyRepository;
    private final WorkConsolidationRepository workConsolidationRepository;

    public PricingPolicyService(PricingPolicyRepository pricingPolicyRepository,
                                WorkConsolidationRepository workConsolidationRepository) {
        this.pricingPolicyRepository = pricingPolicyRepository;
        this.workConsolidationRepository = workConsolidationRepository;
    }

    public List<PricingPolicyResponse> getAll() {
        return PricingPolicyMapper.toResponseList(pricingPolicyRepository.findAll());
    }

    public PricingPolicyResponse getById(UUID id) {
        return PricingPolicyMapper.toResponse(findPolicyOrThrow(id));
    }

    public PricingPolicyResponse create(PricingPolicyRequest req) {
        PricingPolicy policy = PricingPolicyMapper.toEntity(req);
        WorkConsolidation consolidation = findConsolidationOrThrow(req.getWorkConsolidationId());

        policy.setWorkConsolidation(consolidation);
        return PricingPolicyMapper.toResponse(pricingPolicyRepository.save(policy));
    }

    public PricingPolicyResponse update(UUID id, PricingPolicyRequest req) {
        PricingPolicy policy = findPolicyOrThrow(id);
        PricingPolicyMapper.copyToEntity(req, policy);
        return PricingPolicyMapper.toResponse(policy);
    }

    public void deactivate(UUID id) {
        PricingPolicy policy = findPolicyOrThrow(id);
        policy.setActive(false);
    }

    private PricingPolicy findPolicyOrThrow(UUID id) {
        return pricingPolicyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PricingPolicy", "id", id));
    }

    private WorkConsolidation findConsolidationOrThrow(UUID id) {
        return workConsolidationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkConsolidation", "id", id));
    }
}
