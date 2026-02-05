package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.WorkerProduction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface WorkerProductionRepository extends JpaRepository<WorkerProduction, UUID> {

    Page<WorkerProduction> findAll(Pageable pageable);
}
