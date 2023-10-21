package com.clubedebebida.backend.repository;

import com.clubedebebida.backend.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
}
