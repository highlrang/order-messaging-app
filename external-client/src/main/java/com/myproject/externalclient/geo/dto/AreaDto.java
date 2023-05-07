package com.myproject.externalclient.geo.dto;

import lombok.Getter;

@Getter
public class AreaDto {
    
    private long key;
    private String address;
    private String x;
    private String y;

    public void setLocation(String x, String y){
        this.x = x;
        this.y = y;
    }
}
