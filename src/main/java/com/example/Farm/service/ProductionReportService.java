package com.example.Farm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Farm.dto.request.ProductionReportRequest;
import com.example.Farm.dto.response.ProductionReportResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.ProductionReportMapper;
import com.example.Farm.model.ProductionReport;
import com.example.Farm.model.WorkConsolidation;
import com.example.Farm.repository.ProductionReportRepository;
import com.example.Farm.repository.WorkConsolidationRepository;

@Service
@Transactional
public class ProductionReportService {

    private final ProductionReportRepository productionReportRepository;
    private final WorkConsolidationRepository workConsolidationRepository;

    public ProductionReportService(ProductionReportRepository productionReportRepository,
                                   WorkConsolidationRepository workConsolidationRepository) {
        this.productionReportRepository = productionReportRepository;
        this.workConsolidationRepository = workConsolidationRepository;
    }

    public List<ProductionReportResponse> getAll() {
        return ProductionReportMapper.toResponseList(productionReportRepository.findAll());
    }

    public ProductionReportResponse getById(UUID id) {
        return ProductionReportMapper.toResponse(findReportOrThrow(id));
    }

    public ProductionReportResponse create(ProductionReportRequest req) {
        ProductionReport report = ProductionReportMapper.toEntity(req);
        return ProductionReportMapper.toResponse(productionReportRepository.save(report));
    }

    public ProductionReportResponse update(UUID id, ProductionReportRequest req) {
        ProductionReport report = findReportOrThrow(id);
        ProductionReportMapper.copyToEntity(req, report);
        return ProductionReportMapper.toResponse(report);
    }

    public ProductionReportResponse generate(ProductionReportRequest req) {
        ProductionReport report = ProductionReportMapper.toEntity(req);
        WorkConsolidation consolidation = findConsolidationOrThrow(req.getWorkConsolidationId());

        report.setWorkConsolidation(consolidation);
        return ProductionReportMapper.toResponse(productionReportRepository.save(report));
    }

    private ProductionReport findReportOrThrow(UUID id) {
        return productionReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionReport", "id", id));
    }

    private WorkConsolidation findConsolidationOrThrow(UUID id) {
        return workConsolidationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkConsolidation", "id", id));
    }
}
