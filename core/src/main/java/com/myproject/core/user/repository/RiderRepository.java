package com.myproject.core.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.core.user.domain.RiderEntity;

public interface RiderRepository extends JpaRepository<RiderEntity, Long>{
    
    
}
