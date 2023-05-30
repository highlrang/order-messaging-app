package com.myproject.core.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myproject.core.review.domain.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
    
    List<ReviewEntity> findByStoreNo(long storeNo);
}
