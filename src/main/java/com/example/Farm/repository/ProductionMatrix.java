package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ProductionMatrix extends JpaRepository<ProductionMatrix, UUID> {
    Page<ProductionMatrix> findAll(Pageable pageable);
}