package com.myproject.core.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myproject.core.user.domain.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    
    Optional<MemberEntity> findByEmail(String email);
}
