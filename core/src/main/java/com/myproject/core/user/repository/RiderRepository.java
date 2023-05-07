package com.myproject.core.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.core.common.enums.YesNo;
import com.myproject.core.user.domain.RiderEntity;

public interface RiderRepository extends JpaRepository<RiderEntity, Long>{
    
    List<RiderEntity> findAllByActivityYnAndActivityArea(YesNo activityYn, String activityArea);
    
}
