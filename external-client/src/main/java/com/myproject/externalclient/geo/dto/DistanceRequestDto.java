package com.myproject.externalclient.geo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistanceRequestDto {
    
    private List<LocationDto> startLocations;
    private LocationDto destination;
    private int radius; // 길찾기 만경 미터. 최대 10000m
    private String priority; // TIEM | DISTANCE

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class LocationDto{
        private double x;
        private double y;
        private String name;
    }
}
