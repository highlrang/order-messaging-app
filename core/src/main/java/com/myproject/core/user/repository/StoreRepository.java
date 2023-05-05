package com.myproject.core.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myproject.core.user.domain.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, Long>{
    
    @Query(
        "SELECT s FROM StoreEntity s WHERE s.storeId in " 
        + "(SELECT p.storeId FROM ProductEntity p WHERE p.productId in (:productIds))"
        )
    List<StoreEntity> findAllByProductIds(List<Long> productIds);
}
