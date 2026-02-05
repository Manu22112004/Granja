package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.ReportSupervision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ReportSupervisionRepository extends JpaRepository<ReportSupervision, UUID> {

    Page<ReportSupervision> findAll(Pageable pageable);
}
