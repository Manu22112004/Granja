package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.WorkConsolidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface WorkConsolidationRepository extends JpaRepository<WorkConsolidation, UUID> {

    Page<WorkConsolidation> findAll(Pageable pageable);
}
