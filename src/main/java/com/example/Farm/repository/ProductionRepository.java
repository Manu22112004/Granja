package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.Production;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ProductionRepository extends JpaRepository<Production, UUID> {

    Page<Production> findAll(Pageable pageable);
}
