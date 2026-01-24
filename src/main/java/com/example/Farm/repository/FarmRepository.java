package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Page<Farm> findAll(Pageable pageable);
}