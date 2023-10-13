package com.clubedebebida.backend.repository;

import com.clubedebebida.backend.model.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {
}
