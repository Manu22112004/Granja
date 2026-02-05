package com.example.Farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    Page<Person> findAll(Pageable pageable);
}
