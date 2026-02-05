package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.ReportEmployeeEntryRequest;
import com.example.Farm.dto.response.ReportEmployeeEntryResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.ReportEmployeeEntryMapper;
import com.example.Farm.model.ProductionReport;
import com.example.Farm.model.ReportEmployeeEntry;
import com.example.Farm.repository.ProductionReportRepository;
import com.example.Farm.repository.ReportEmployeeEntryRepository;

@Service
@Transactional
public class ReportEmployeeEntryService {

    private final ReportEmployeeEntryRepository reportEmployeeEntryRepository;
    private final ProductionReportRepository productionReportRepository;

    public ReportEmployeeEntryService(ReportEmployeeEntryRepository reportEmployeeEntryRepository,
                                      ProductionReportRepository productionReportRepository) {
        this.reportEmployeeEntryRepository = reportEmployeeEntryRepository;
        this.productionReportRepository = productionReportRepository;
    }

    public List<ReportEmployeeEntryResponse> getAll() {
        return ReportEmployeeEntryMapper.toResponseList(reportEmployeeEntryRepository.findAll());
    }

    public ReportEmployeeEntryResponse getById(UUID id) {
        return ReportEmployeeEntryMapper.toResponse(findEntryOrThrow(id));
    }

    public ReportEmployeeEntryResponse create(ReportEmployeeEntryRequest req) {
        ReportEmployeeEntry entry = ReportEmployeeEntryMapper.toEntity(req);
        ProductionReport report = findReportOrThrow(req.getProductionReportId());
        entry.setProductionReport(report);
        return ReportEmployeeEntryMapper.toResponse(reportEmployeeEntryRepository.save(entry));
    }

    public ReportEmployeeEntryResponse update(UUID id, ReportEmployeeEntryRequest req) {
        ReportEmployeeEntry entry = findEntryOrThrow(id);
        ReportEmployeeEntryMapper.copyToEntity(req, entry);
        return ReportEmployeeEntryMapper.toResponse(entry);
    }

    private ReportEmployeeEntry findEntryOrThrow(UUID id) {
        return reportEmployeeEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReportEmployeeEntry", "id", id));
    }

    private ProductionReport findReportOrThrow(UUID id) {
        return productionReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionReport", "id", id));
    }
}
