package com.myproject.core.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.core.delivery.domain.DeliveryHistoryEntity;

public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistoryEntity, Long>{
    
}
