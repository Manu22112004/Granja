package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.CompanyRequest;
import com.example.Farm.dto.response.CompanyResponse;
import com.example.Farm.model.Company;

public final class CompanyMapper {

    private CompanyMapper() {}

    public static CompanyResponse toResponse(Company company) {
        if (company == null) return null;

        return CompanyResponse.builder()
                .companyId(company.getCompanyId())
                .name(company.getName())
                .taxId(company.getTaxId())
                .active(company.getActive())
                .build();
    }

    public static List<CompanyResponse> toResponseList(List<Company> companies) {
        if (companies == null || companies.isEmpty()) return List.of();
        return companies.stream().map(CompanyMapper::toResponse).toList();
    }

    public static Company toEntity(CompanyRequest request) {
        if (request == null) return null;
        Company company = new Company();
        apply(request, company);
        return company;
    }

    public static void copyToEntity(CompanyRequest request, Company entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(CompanyRequest request, Company company) {
        if (request.getName() != null) company.setName(request.getName());
        if (request.getTaxId() != null) company.setTaxId(request.getTaxId());
        if (request.getActive() != null) company.setActive(request.getActive());
    }
}
