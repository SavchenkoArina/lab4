package com.example.lab3.repository;

import com.example.lab3.model.UtilityCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityChargeRepository extends JpaRepository<UtilityCharge, Long> {
}