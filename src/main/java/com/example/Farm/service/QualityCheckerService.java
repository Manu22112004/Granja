package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.QualityCheckerRequest;
import com.example.Farm.dto.response.QualityCheckerResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.QualityCheckerMapper;
import com.example.Farm.model.QualityChecker;
import com.example.Farm.repository.QualityCheckerRepository;

@Service
@Transactional
public class QualityCheckerService {

    private final QualityCheckerRepository qualityCheckerRepository;

    public QualityCheckerService(QualityCheckerRepository qualityCheckerRepository) {
        this.qualityCheckerRepository = qualityCheckerRepository;
    }

    public List<QualityCheckerResponse> getAll() {
        return QualityCheckerMapper.toResponseList(qualityCheckerRepository.findAll());
    }

    public QualityCheckerResponse getById(UUID id) {
        return QualityCheckerMapper.toResponse(findCheckerOrThrow(id));
    }

    public QualityCheckerResponse create(QualityCheckerRequest req) {
        QualityChecker checker = QualityCheckerMapper.toEntity(req);
        return QualityCheckerMapper.toResponse(qualityCheckerRepository.save(checker));
    }

    public QualityCheckerResponse update(UUID id, QualityCheckerRequest req) {
        QualityChecker checker = findCheckerOrThrow(id);
        QualityCheckerMapper.copyToEntity(req, checker);
        return QualityCheckerMapper.toResponse(checker);
    }

    private QualityChecker findCheckerOrThrow(UUID id) {
        return qualityCheckerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QualityChecker", "id", id));
    }
}
