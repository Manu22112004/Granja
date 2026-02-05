package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.ReportSupervisionRequest;
import com.example.Farm.dto.response.ReportSupervisionResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.ReportSupervisionMapper;
import com.example.Farm.model.ProductionReport;
import com.example.Farm.model.ReportSupervision;
import com.example.Farm.repository.ProductionReportRepository;
import com.example.Farm.repository.ReportSupervisionRepository;

@Service
@Transactional
public class ReportSupervisionService {

    private final ReportSupervisionRepository reportSupervisionRepository;
    private final ProductionReportRepository productionReportRepository;

    public ReportSupervisionService(ReportSupervisionRepository reportSupervisionRepository,
                                    ProductionReportRepository productionReportRepository) {
        this.reportSupervisionRepository = reportSupervisionRepository;
        this.productionReportRepository = productionReportRepository;
    }

    public List<ReportSupervisionResponse> getAll() {
        return ReportSupervisionMapper.toResponseList(reportSupervisionRepository.findAll());
    }

    public ReportSupervisionResponse getById(UUID id) {
        return ReportSupervisionMapper.toResponse(findSupervisionOrThrow(id));
    }

    public ReportSupervisionResponse create(ReportSupervisionRequest req) {
        ReportSupervision supervision = ReportSupervisionMapper.toEntity(req);
        ProductionReport report = findReportOrThrow(req.getProductionReportId());
        supervision.setProductionReport(report);
        return ReportSupervisionMapper.toResponse(reportSupervisionRepository.save(supervision));
    }

    public ReportSupervisionResponse update(UUID id, ReportSupervisionRequest req) {
        ReportSupervision supervision = findSupervisionOrThrow(id);
        ReportSupervisionMapper.copyToEntity(req, supervision);
        return ReportSupervisionMapper.toResponse(supervision);
    }

    private ReportSupervision findSupervisionOrThrow(UUID id) {
        return reportSupervisionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReportSupervision", "id", id));
    }

    private ProductionReport findReportOrThrow(UUID id) {
        return productionReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionReport", "id", id));
    }
}
