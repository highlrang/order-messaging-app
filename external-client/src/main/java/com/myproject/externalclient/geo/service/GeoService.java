package com.myproject.externalclient.geo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myproject.externalclient.geo.client.KakaoMapClient;
import com.myproject.externalclient.geo.client.KakaoMobilityClient;
import com.myproject.externalclient.geo.dto.DistanceRequestDto;
import com.myproject.externalclient.geo.dto.DistanceResponseDto;
import com.myproject.externalclient.geo.dto.LocationResponseDto;
import com.myproject.externalclient.geo.dto.RiderAreaDto;
import com.myproject.externalclient.geo.dto.LocationResponseDto.Document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoService {
    
    private final KakaoMapClient kakaoMapClient;
    private final KakaoMobilityClient kakaoMobilityClient;

    public List<RiderAreaDto> getLocation(List<RiderAreaDto> riderAreaDtos){
        
        for (RiderAreaDto riderAreaDto : riderAreaDtos) {
            
            ResponseEntity<LocationResponseDto> result = kakaoMapClient.getLocation(riderAreaDto.getAddress());
            
            if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null){
                LocationResponseDto locationResponseDto = result.getBody();

                if(!locationResponseDto.getDocuments().isEmpty()){
                    LocationResponseDto.Document document = locationResponseDto.getDocuments().get(0);    
                    riderAreaDto.setLocation(document.getX(), document.getY());
                }else{
                    log.error("[ExternalClient] Feign Response Data Null Error \n {}", locationResponseDto.toString());
                }
                
            }else{
                log.error("[ExternalClient] Feign Response Error \n {}", result.toString());
            }
        }
        
        return riderAreaDtos;
    }

    public DistanceResponseDto getDistance(List<RiderAreaDto> riderAreaDtos){

        DistanceRequestDto distanceRequestDto = DistanceRequestDto.builder().build();
        ResponseEntity<DistanceResponseDto> result = kakaoMobilityClient.getDistance(distanceRequestDto);
        if(!result.getStatusCode().is2xxSuccessful()){
            log.error("[ExternalClient] Feign Response Error \n {}", result.toString());
            return null;
        }

        return result.getBody();
        
    }


}
