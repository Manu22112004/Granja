package com.example.Farm.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Farm.dto.request.WorkConsolidationRequest;
import com.example.Farm.dto.response.WorkConsolidationResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.WorkConsolidationMapper;
import com.example.Farm.model.Company;
import com.example.Farm.model.CrewLeader;
import com.example.Farm.model.Customer;
import com.example.Farm.model.PricingPolicy;
import com.example.Farm.model.Production;
import com.example.Farm.model.ProductionMatrix;
import com.example.Farm.model.ProductionReport;
import com.example.Farm.model.QualityChecker;
import com.example.Farm.model.WorkConsolidation;
import com.example.Farm.repository.CompanyRepository;
import com.example.Farm.repository.CrewLeaderRepository;
import com.example.Farm.repository.CustomerRepository;
import com.example.Farm.repository.PricingPolicyRepository;
import com.example.Farm.repository.ProductionMatrixRepository;
import com.example.Farm.repository.ProductionReportRepository;
import com.example.Farm.repository.ProductionRepository;
import com.example.Farm.repository.QualityCheckerRepository;
import com.example.Farm.repository.WorkConsolidationRepository;

@Service
@Transactional
public class WorkConsolidationService {

    private final WorkConsolidationRepository workConsolidationRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ProductionRepository productionRepository;
    private final ProductionMatrixRepository productionMatrixRepository;
    private final PricingPolicyRepository pricingPolicyRepository;
    private final ProductionReportRepository productionReportRepository;
    private final CrewLeaderRepository crewLeaderRepository;
    private final QualityCheckerRepository qualityCheckerRepository;

    public WorkConsolidationService(WorkConsolidationRepository workConsolidationRepository,
                                    CompanyRepository companyRepository,
                                    CustomerRepository customerRepository,
                                    ProductionRepository productionRepository,
                                    ProductionMatrixRepository productionMatrixRepository,
                                    PricingPolicyRepository pricingPolicyRepository,
                                    ProductionReportRepository productionReportRepository,
                                    CrewLeaderRepository crewLeaderRepository,
                                    QualityCheckerRepository qualityCheckerRepository) {
        this.workConsolidationRepository = workConsolidationRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.productionRepository = productionRepository;
        this.productionMatrixRepository = productionMatrixRepository;
        this.pricingPolicyRepository = pricingPolicyRepository;
        this.productionReportRepository = productionReportRepository;
        this.crewLeaderRepository = crewLeaderRepository;
        this.qualityCheckerRepository = qualityCheckerRepository;
    }

    public List<WorkConsolidationResponse> getAll() {
        return WorkConsolidationMapper.toResponseList(workConsolidationRepository.findAll());
    }

    public WorkConsolidationResponse getById(UUID id) {
        return WorkConsolidationMapper.toResponse(findConsolidationOrThrow(id));
    }

    public WorkConsolidationResponse create(WorkConsolidationRequest req) {

        WorkConsolidation consolidation = WorkConsolidationMapper.toEntity(req);

        Company company = findCompanyOrThrow(req.getCompanyId());
        Customer customer = findCustomerOrThrow(req.getCustomerId());

        consolidation.setCompany(company);
        consolidation.setCustomer(customer);

        if (req.getProductionId() != null) {
            consolidation.setProduction(
                    findProductionOrThrow(req.getProductionId())
            );
        }

        if (req.getProductionMatrixId() != null) {
            consolidation.setProductionMatrix(
                    findProductionMatrixOrThrow(req.getProductionMatrixId())
            );
        }

        if (req.getPricingPolicyId() != null) {
            consolidation.setPricingPolicy(
                    findPricingPolicyOrThrow(req.getPricingPolicyId())
            );
        }

        if (req.getProductionReportId() != null) {
            consolidation.setProductionReport(
                    findProductionReportOrThrow(req.getProductionReportId())
            );
        }

        if (req.getCrewLeaderId() != null) {
            consolidation.setCrewLeader(
                findCrewLeaderOrThrow(req.getCrewLeaderId())
            );
        }

        if (req.getQualityCheckerId() != null) {
            consolidation.setQualityChecker(
                findQualityCheckerOrThrow(req.getQualityCheckerId())
            );
        }


        return WorkConsolidationMapper.toResponse(
                workConsolidationRepository.save(consolidation)
        );
    }


    @Transactional
    public WorkConsolidationResponse update(UUID id, WorkConsolidationRequest req) {

        WorkConsolidation consolidation = findConsolidationOrThrow(id);
        WorkConsolidationMapper.copyToEntity(req, consolidation);

        if (req.getProductionMatrixId() != null) {
            ProductionMatrix matrix = productionMatrixRepository
                .findById(req.getProductionMatrixId())
                .orElseThrow(() -> new RuntimeException("ProductionMatrix not found"));

            consolidation.setProductionMatrix(matrix);
        } else {
            consolidation.setProductionMatrix(null);
        }

        if (req.getCrewLeaderId() != null) {
            CrewLeader crewLeader = crewLeaderRepository
                .findById(req.getCrewLeaderId())
                .orElseThrow(() -> new RuntimeException("CrewLeader not found"));

            consolidation.setCrewLeader(crewLeader);
        } else {
            consolidation.setCrewLeader(null);
        }

        if (req.getQualityCheckerId() != null) {
            QualityChecker qualityChecker = qualityCheckerRepository
                .findById(req.getQualityCheckerId())
                .orElseThrow(() -> new RuntimeException("QualityChecker not found"));

            consolidation.setQualityChecker(qualityChecker);
        } else {
            consolidation.setQualityChecker(null);
        }
        return WorkConsolidationMapper.toResponse(consolidation);
    }

    public WorkConsolidationResponse close(UUID id) {
        WorkConsolidation consolidation = findConsolidationOrThrow(id);
        consolidation.setClosed(true);
        return WorkConsolidationMapper.toResponse(consolidation);
    }

    /*-------------------
           HELPERS
    -------------------*/
    private WorkConsolidation findConsolidationOrThrow(UUID id) {
        return workConsolidationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkConsolidation", "id", id));
    }

    private Company findCompanyOrThrow(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
    }

    private Customer findCustomerOrThrow(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }

    private Production findProductionOrThrow(UUID id) {
        return productionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Production", "id", id));
    }

    private ProductionMatrix findProductionMatrixOrThrow(UUID id) {
        return productionMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionMatrix", "id", id));
    }

    private PricingPolicy findPricingPolicyOrThrow(UUID id) {
        return pricingPolicyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PricingPolicy", "id", id));
    }
    private ProductionReport findProductionReportOrThrow(UUID id) {
        return productionReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionReport", "id", id));
    }

    private CrewLeader findCrewLeaderOrThrow(UUID id) {
        return crewLeaderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CrewLeader", "id", id));
    }

    private QualityChecker findQualityCheckerOrThrow(UUID id) {
        return qualityCheckerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QualityChecker", "id", id));
    }
}
