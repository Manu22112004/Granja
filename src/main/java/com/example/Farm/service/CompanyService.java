package com.example.Farm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Farm.dto.request.CompanyRequest;
import com.example.Farm.dto.response.CompanyResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.CompanyMapper;
import com.example.Farm.model.Company;
import com.example.Farm.repository.CompanyRepository;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyResponse> getAll() {
        return CompanyMapper.toResponseList(companyRepository.findAll());
    }

    public CompanyResponse getById(UUID id) {
        return CompanyMapper.toResponse(findCompanyOrThrow(id));
    }

    public CompanyResponse create(CompanyRequest req) {
        Company company = CompanyMapper.toEntity(req);
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    public CompanyResponse update(UUID id, CompanyRequest req) {
        Company company = findCompanyOrThrow(id);
        CompanyMapper.copyToEntity(req, company);
        return CompanyMapper.toResponse(company);
    }

    public void deactivate(UUID id) {
        Company company = findCompanyOrThrow(id);
        company.setActive(false);
    }

    private Company findCompanyOrThrow(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
    }
}
