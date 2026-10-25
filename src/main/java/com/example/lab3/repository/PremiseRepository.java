package com.example.lab3.repository;

import com.example.lab3.model.Premise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiseRepository extends JpaRepository<Premise, Long> {
}