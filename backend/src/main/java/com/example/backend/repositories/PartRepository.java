package com.example.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.Part;

public interface PartRepository extends JpaRepository<Part, Long> {
}