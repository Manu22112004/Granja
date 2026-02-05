package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.ReportOrganizationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ReportOrganizationInfoRepository extends JpaRepository<ReportOrganizationInfo, UUID> {

    Page<ReportOrganizationInfo> findAll(Pageable pageable);
}
