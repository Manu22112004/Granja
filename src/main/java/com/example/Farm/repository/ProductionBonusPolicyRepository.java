package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.ProductionBonusPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ProductionBonusPolicyRepository extends JpaRepository<ProductionBonusPolicy, UUID> {
    
    Page<ProductionBonusPolicy> findAll(Pageable pageable);
}