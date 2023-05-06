package com.myproject.externalclient.geo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myproject.externalclient.geo.dto.DistanceResponseDto;
import com.myproject.externalclient.geo.dto.LocationResponseDto;

@FeignClient(name = "kakao-map", url = "${kakao.maps.domain}")
public interface KakaoMapClient {
    
    @GetMapping("/v2/local/search/address")
    ResponseEntity<LocationResponseDto> getLocation(@RequestParam("query") String locationName);
}
