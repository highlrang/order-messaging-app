package com.myproject.externalclient.geo.dto;

import lombok.Getter;

@Getter
public class RiderAreaDto {
    
    private long riderId;
    private String address;
    private String x;
    private String y;

    public void setLocation(String x, String y){
        this.x = x;
        this.y = y;
    }
}
