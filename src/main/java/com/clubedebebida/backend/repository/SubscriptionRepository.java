package com.clubedebebida.backend.repository;

import com.clubedebebida.backend.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
