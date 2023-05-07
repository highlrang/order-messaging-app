package com.myproject.storeapi.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.myproject.core.delivery.dto.AreaDto;
import com.myproject.storeapi.delivery.dto.DistanceRequestDto;
import com.myproject.storeapi.delivery.dto.DistanceResponseDto;

@FeignClient(name = "external-client", url="${externalClient.uri}")
public interface ExternalClient {
    
    @PostMapping(value = "/geo/location", produces = "application/json; charset=UTF-8")
    ResponseEntity<List<AreaDto>> getLocation(@RequestBody List<AreaDto> areaDtos);

    @PostMapping(value = "/geo/distance", produces = "application/json; charset=UTF-8")
    ResponseEntity<DistanceResponseDto> getDistance(@RequestBody DistanceRequestDto distanceRequestDto);
}
