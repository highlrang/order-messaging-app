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
    
    private List<Origin> origins;
    private Destination destination;
    private int radius; // 길찾기 반경 미터. 최대 10000m
    private String priority; // TIME | DISTANCE

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class Origin{
        private double x;
        private double y;
        private String key;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class Destination{
        private double x;
        private double y;
        private String name;
    }
}
