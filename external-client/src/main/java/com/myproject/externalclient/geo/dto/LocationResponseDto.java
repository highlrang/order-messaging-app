package com.myproject.externalclient.geo.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class LocationResponseDto {
    
    private Meta meta;
    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Meta {
        private int total_count;
        private int pageable_count;
        private boolean is_end;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Document {
        private String address_name;
        private String x;
        private String y;
        private String address_type; // REGION | ROAD | REGION_ADDR | ROAD_ADDR
        private Address address; // 지번 주소 상세 정보
        private RoadAddress road_address; // 도로명 주소 상세 정보
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Address {
        private String address_name;
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String region_3depth_h_name;

        private String h_code;
        private String b_code;
        private String mountain_yn;
        private String main_address_no;
        private String sub_address_no;
        private String x;
        private String y;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class RoadAddress {
        private String address_name;
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String road_name;
        private String underground_yn;
        private String main_building_no;
        private String sub_building_no;
        private String building_name;
        private String zone_no;
        private String y;
        private String x;
    }
}
