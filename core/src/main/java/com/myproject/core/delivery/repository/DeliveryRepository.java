package com.myproject.core.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.core.delivery.domain.DeliveryEntity;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long>{
    
}
