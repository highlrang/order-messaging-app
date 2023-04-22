package com.myproject.core.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.core.order.domain.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
    
}
