package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface WorkerRepository extends JpaRepository<Worker, UUID> {

    Page<Worker> findAll(Pageable pageable);
}
