package com.myproject.core.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.core.order.domain.OrderProductEntity;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
    
    List<OrderProductEntity> findByOrderId(long orderId);
}
