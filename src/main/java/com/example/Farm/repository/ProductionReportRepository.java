package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.ProductionReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ProductionReportRepository extends JpaRepository<ProductionReport, UUID> {

    Page<ProductionReport> findAll(Pageable pageable);
}
