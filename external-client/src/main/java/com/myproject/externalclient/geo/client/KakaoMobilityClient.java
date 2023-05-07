package com.myproject.externalclient.geo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.myproject.externalclient.geo.config.KakaoHeaderConfig;
import com.myproject.externalclient.geo.dto.DistanceRequestDto;
import com.myproject.externalclient.geo.dto.DistanceResponseDto;

@FeignClient(name = "kakao-mobility", url = "${kakao.mobility.domain}", configuration = KakaoHeaderConfig.class)
public interface KakaoMobilityClient {
    
    @PostMapping("/v1/origins/directions")
    ResponseEntity<DistanceResponseDto> getDistance(@RequestBody DistanceRequestDto distanceRequestDto);

}
