package com.example.Farm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Farm.dto.request.WorkerRequest;
import com.example.Farm.dto.response.WorkerResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.WorkerMapper;
import com.example.Farm.model.Worker;
import com.example.Farm.repository.WorkerRepository;

@Service
@Transactional
public class WorkerService {

    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public List<WorkerResponse> getAll() {
        return WorkerMapper.toResponseList(workerRepository.findAll());
    }

    public WorkerResponse getById(UUID id) {
        return WorkerMapper.toResponse(findWorkerOrThrow(id));
    }

    public WorkerResponse create(WorkerRequest req) {
        Worker worker = WorkerMapper.toEntity(req);
        return WorkerMapper.toResponse(workerRepository.save(worker));
    }

    public WorkerResponse update(UUID id, WorkerRequest req) {
        Worker worker = findWorkerOrThrow(id);
        WorkerMapper.copyToEntity(req, worker);
        return WorkerMapper.toResponse(worker);
    }

    public void deactivate(UUID id) {
        Worker worker = findWorkerOrThrow(id);
        worker.setActive(false);
    }

    public void delete(UUID id) {
        Worker worker = findWorkerOrThrow(id);
        workerRepository.delete(worker);
    }

    private Worker findWorkerOrThrow(UUID id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", id));
    }
}
