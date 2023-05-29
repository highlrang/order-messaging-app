package com.myproject.core.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myproject.core.user.domain.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, Long>{
    
    @Query(
        "SELECT s FROM StoreEntity s WHERE s.storeNo in " 
        + "(SELECT p.storeNo FROM ProductEntity p WHERE p.productId in (:productIds))"
        )
    List<StoreEntity> findAllByProductIds(List<Long> productIds);

    @Query(
        "SELECT s FROM StoreEntity s JOIN ProductEntity p ON s.storeNo = p.storeNo "
        + "JOIN OrderProductEntity op ON p.productId = op.productId "
        + "WHERE op.orderProductId = :orderProductId"
        )
    Optional<StoreEntity> findByOrderProductId(long orderProdcutId);
}
