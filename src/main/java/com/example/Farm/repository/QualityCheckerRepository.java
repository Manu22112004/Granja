package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.QualityChecker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface QualityCheckerRepository extends JpaRepository<QualityChecker, UUID> {

    Page<QualityChecker> findAll(Pageable pageable);
}
