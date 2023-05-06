package com.myproject.core.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderAreaDto {
    
    private long riderId;
    private String address;
    private String x;
    private String y;
}
