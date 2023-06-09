package com.myproject.core.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myproject.core.delivery.domain.DeliveryEntity;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long>{
    
    @Query("SELECT d FROM DeliveryEntity d WHERE d.orderId = :orderId")
    Optional<DeliveryEntity> findByOrderId(@Param("orderId") long orderId);
}
