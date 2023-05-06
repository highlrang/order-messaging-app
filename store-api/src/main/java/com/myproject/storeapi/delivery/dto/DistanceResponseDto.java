package com.myproject.storeapi.delivery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class DistanceResponseDto {
    
    private String trans_id;
    private List<Route> routes;

    @Getter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    public static class Route {
        private int result_code;
        private String result_msg;
        private String key;
        private Summary summary;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    public static class Summary {
        private int distance;
        private int duration;
    }
}
