package com.myproject.core.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.core.review.domain.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
    
}
