package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.CrewLeaderRequest;
import com.example.Farm.dto.response.CrewLeaderResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.CrewLeaderMapper;
import com.example.Farm.model.CrewLeader;
import com.example.Farm.repository.CrewLeaderRepository;

@Service
@Transactional
public class CrewLeaderService {

    private final CrewLeaderRepository crewLeaderRepository;

    public CrewLeaderService(CrewLeaderRepository crewLeaderRepository) {
        this.crewLeaderRepository = crewLeaderRepository;
    }

    public List<CrewLeaderResponse> getAll() {
        return CrewLeaderMapper.toResponseList(crewLeaderRepository.findAll());
    }

    public CrewLeaderResponse getById(UUID id) {
        return CrewLeaderMapper.toResponse(findLeaderOrThrow(id));
    }

    public CrewLeaderResponse create(CrewLeaderRequest req) {
        CrewLeader leader = CrewLeaderMapper.toEntity(req);
        return CrewLeaderMapper.toResponse(crewLeaderRepository.save(leader));
    }

    public CrewLeaderResponse update(UUID id, CrewLeaderRequest req) {
        CrewLeader leader = findLeaderOrThrow(id);
        CrewLeaderMapper.copyToEntity(req, leader);
        return CrewLeaderMapper.toResponse(leader);
    }

    private CrewLeader findLeaderOrThrow(UUID id) {
        return crewLeaderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CrewLeader", "id", id));
    }
}
