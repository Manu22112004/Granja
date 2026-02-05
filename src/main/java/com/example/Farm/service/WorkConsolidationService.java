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
import com.example.Farm.model.Customer;
import com.example.Farm.model.WorkConsolidation;
import com.example.Farm.repository.CompanyRepository;
import com.example.Farm.repository.CustomerRepository;
import com.example.Farm.repository.WorkConsolidationRepository;

@Service
@Transactional
public class WorkConsolidationService {

    private final WorkConsolidationRepository workConsolidationRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    public WorkConsolidationService(WorkConsolidationRepository workConsolidationRepository,
                                    CompanyRepository companyRepository,
                                    CustomerRepository customerRepository) {
        this.workConsolidationRepository = workConsolidationRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
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

        return WorkConsolidationMapper.toResponse(workConsolidationRepository.save(consolidation));
    }

    public WorkConsolidationResponse update(UUID id, WorkConsolidationRequest req) {
        WorkConsolidation consolidation = findConsolidationOrThrow(id);
        WorkConsolidationMapper.copyToEntity(req, consolidation);
        return WorkConsolidationMapper.toResponse(consolidation);
    }

    public WorkConsolidationResponse close(UUID id) {
        WorkConsolidation consolidation = findConsolidationOrThrow(id);
        consolidation.setClosed(true);
        return WorkConsolidationMapper.toResponse(consolidation);
    }

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
}
