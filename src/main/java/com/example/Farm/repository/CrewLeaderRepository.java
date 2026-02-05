package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.CrewLeader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface CrewLeaderRepository extends JpaRepository<CrewLeader, UUID> {

    Page<CrewLeader> findAll(Pageable pageable);
}
