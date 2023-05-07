package com.myproject.externalclient.geo.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class DistanceResponseDto {
    
    private String trans_id;
    private List<Route> routes;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Route {
        private int result_code;
        private String result_msg;
        private String key;
        private Summary summary;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Summary {
        private int distance;
        private int duration;
    }
}
