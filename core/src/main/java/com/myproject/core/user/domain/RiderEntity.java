package com.myproject.core.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "RIDER")
public class RiderEntity extends UserEntity{
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long riderId;

    @NotNull
    private String activityArea;
    
}
