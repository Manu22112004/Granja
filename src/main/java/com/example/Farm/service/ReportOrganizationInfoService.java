package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.ReportOrganizationInfoRequest;
import com.example.Farm.dto.response.ReportOrganizationInfoResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.ReportOrganizationInfoMapper;
import com.example.Farm.model.ProductionReport;
import com.example.Farm.model.ReportOrganizationInfo;
import com.example.Farm.repository.ProductionReportRepository;
import com.example.Farm.repository.ReportOrganizationInfoRepository;

@Service
@Transactional
public class ReportOrganizationInfoService {

    private final ReportOrganizationInfoRepository reportOrganizationInfoRepository;
    private final ProductionReportRepository productionReportRepository;

    public ReportOrganizationInfoService(ReportOrganizationInfoRepository reportOrganizationInfoRepository,
                                         ProductionReportRepository productionReportRepository) {
        this.reportOrganizationInfoRepository = reportOrganizationInfoRepository;
        this.productionReportRepository = productionReportRepository;
    }

    public List<ReportOrganizationInfoResponse> getAll() {
        return ReportOrganizationInfoMapper.toResponseList(reportOrganizationInfoRepository.findAll());
    }

    public ReportOrganizationInfoResponse getById(UUID id) {
        return ReportOrganizationInfoMapper.toResponse(findInfoOrThrow(id));
    }

    public ReportOrganizationInfoResponse create(ReportOrganizationInfoRequest req) {
        ReportOrganizationInfo info = ReportOrganizationInfoMapper.toEntity(req);
        ProductionReport report = findReportOrThrow(req.getProductionReportId());
        info.setProductionReport(report);
        return ReportOrganizationInfoMapper.toResponse(reportOrganizationInfoRepository.save(info));
    }

    public ReportOrganizationInfoResponse update(UUID id, ReportOrganizationInfoRequest req) {
        ReportOrganizationInfo info = findInfoOrThrow(id);
        ReportOrganizationInfoMapper.copyToEntity(req, info);
        return ReportOrganizationInfoMapper.toResponse(info);
    }

    private ReportOrganizationInfo findInfoOrThrow(UUID id) {
        return reportOrganizationInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReportOrganizationInfo", "id", id));
    }

    private ProductionReport findReportOrThrow(UUID id) {
        return productionReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionReport", "id", id));
    }
}
