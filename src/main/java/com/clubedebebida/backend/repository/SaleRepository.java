package com.clubedebebida.backend.repository;

import com.clubedebebida.backend.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
