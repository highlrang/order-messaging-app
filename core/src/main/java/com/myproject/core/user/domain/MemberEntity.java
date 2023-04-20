package com.myproject.core.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.myproject.core.common.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity extends UserEntity{
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long memberId;

    @NotNull
    private String memberName;

    @NotNull
    private String phoneNumber;

    
}
