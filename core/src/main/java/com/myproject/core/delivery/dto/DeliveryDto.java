package com.myproject.core.delivery.dto;

import com.myproject.core.delivery.domain.DeliveryEntity;

import lombok.Getter;

@Getter
public class DeliveryDto {
    
    private long riderId;
    private String address;

    public DeliveryDto(DeliveryEntity e){
        this.riderId = e.getRiderId();
        this.address = e.getAddress();
    }
    
}
