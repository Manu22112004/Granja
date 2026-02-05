package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.ReportEmployeeEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ReportEmployeeEntryRepository extends JpaRepository<ReportEmployeeEntry, UUID> {

    Page<ReportEmployeeEntry> findAll(Pageable pageable);
}
