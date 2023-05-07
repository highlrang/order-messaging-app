package com.myproject.storeapi.delivery.dto;

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
    public static class Origin{
        private double x;
        private double y;
        private String key;

        public static Origin create(String x, String y, String key){
            return new Origin(Double.valueOf(x), Double.valueOf(y), key);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Destination{
        private double x;
        private double y;
        private String name;

        public static Destination create(String x, String y, String name){
            return new Destination(Double.valueOf(x), Double.valueOf(y), name);
        }
    }

    public static DistanceRequestDto create(Destination destination, List<Origin> origins){
        return DistanceRequestDto.builder()
            .destination(destination)
            .origins(origins)
            .radius(10000)
            .priority("TIME")
            .build();
    }
}
