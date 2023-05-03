package com.myproject.core.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.core.product.domain.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    
}
