package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.PlannedBed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface PlannedBedRepository extends JpaRepository<PlannedBed, UUID> {
    Page<PlannedBed> findAll(Pageable pageable);
}