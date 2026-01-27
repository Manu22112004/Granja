package com.example.Farm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Farm.dto.request.FarmRequest;
import com.example.Farm.dto.response.FarmResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.FarmMapper;
import com.example.Farm.model.Farm;
import com.example.Farm.repository.FarmRepository;

@Service
@Transactional
public class FarmService {

    private final FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public List<FarmResponse> getAll() {
        return FarmMapper.toResponseList(farmRepository.findAll());
    }

    public FarmResponse getById(UUID id) {
        return FarmMapper.toResponse(findFarmOrThrow(id));
    }

    public FarmResponse create(FarmRequest req) {
        Farm farm = FarmMapper.toEntity(req);
        return FarmMapper.toResponse(farmRepository.save(farm));
    }

    public FarmResponse update(UUID id, FarmRequest req) {
        Farm farm = findFarmOrThrow(id);
        FarmMapper.copyToEntity(req, farm);
        return FarmMapper.toResponse(farm); // dirty checking
    }

    private Farm findFarmOrThrow(UUID id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", "id", id));
    }
}

