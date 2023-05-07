package com.myproject.externalclient.geo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.externalclient.geo.client.KakaoMapClient;
import com.myproject.externalclient.geo.client.KakaoMobilityClient;
import com.myproject.externalclient.geo.dto.AreaDto;
import com.myproject.externalclient.geo.dto.DistanceRequestDto;
import com.myproject.externalclient.geo.dto.DistanceResponseDto;
import com.myproject.externalclient.geo.service.GeoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/geo")
public class GeoController {
    
    private final GeoService geoService;

    @PostMapping("/location")
    public ResponseEntity<?> getLocation(@RequestBody List<AreaDto> areaDtos){

        return ResponseEntity.ok(geoService.getLocation(areaDtos));
    } 

    @PostMapping("/distance")
    public ResponseEntity<?> getDistance(@RequestBody DistanceRequestDto distanceRequestDto) {

        DistanceResponseDto distanceResponseDto = geoService.getDistance(distanceRequestDto);
        if(distanceResponseDto == null)
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(distanceResponseDto);
    }

}
