package com.myproject.core.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaDto {
    
    private String address;
    private String x;
    private String y;
    private long key;

    public AreaDto(String address){
        this.address = address;
    }

    public AreaDto(String address, long key){
        this.address = address;
        this.key = key;
    }
}
