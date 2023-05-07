package com.myproject.storeapi.delivery.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.enums.YesNo;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.delivery.domain.DeliveryEntity;
import com.myproject.core.delivery.dto.AreaDto;
import com.myproject.core.delivery.dto.DeliveryDto;
import com.myproject.core.delivery.repository.DeliveryRepository;
import com.myproject.core.user.domain.RiderEntity;
import com.myproject.core.user.repository.RiderRepository;
import com.myproject.storeapi.client.ExternalClient;
import com.myproject.storeapi.delivery.dto.DistanceRequestDto;
import com.myproject.storeapi.delivery.dto.DistanceResponseDto;
import com.myproject.storeapi.delivery.dto.DistanceRequestDto.Destination;
import com.myproject.storeapi.delivery.dto.DistanceRequestDto.Origin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryMatchingService {
    
    private final DeliveryRepository deliveryRepository;
    private final RiderRepository riderRepository;
    private final ExternalClient externalClient;

    public RiderEntity selectDeliveryRiderByDistance(List<RiderEntity> riderEntities, String deliveryAddress){

        List<AreaDto> riderAreaResponseDtos = getRiderAreaDtos(riderEntities);
        AreaDto startAreaDto = getStartAreaDto(deliveryAddress);

        DistanceResponseDto distanceResponseDto = getDistance(startAreaDto, riderAreaResponseDtos);
        
        Long selectedRiderId = selectRider(distanceResponseDto);

        return riderEntities.stream()
            .filter(rider -> rider.getUserId() == selectedRiderId)
            .findAny()
            .orElseThrow(() -> new CustomException(ErrorCode.RIDER_MATCHING_ERROR));
    }

    private AreaDto getStartAreaDto(String deliveryAddress){
        ResponseEntity<?> startLocationResult = externalClient.getLocation(Arrays.asList(AreaDto.builder().address(deliveryAddress).build()));
        List<AreaDto> startAreaResponseDtos = (List<AreaDto>) startLocationResult.getBody();
        if(startAreaResponseDtos.isEmpty()) {
            log.error("[RIDER MATCHING ERROR ===> START LOCATION EMPTY]");
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return startAreaResponseDtos.get(0);
    }

    private List<AreaDto> getRiderAreaDtos(List<RiderEntity> riderEntities){
        List<AreaDto> riderAreaRequestDtos = riderEntities.stream()
            .map(rider -> new AreaDto(rider.getNowArea(), rider.getUserId()))
            .collect(Collectors.toList());

        ResponseEntity<?> destinationResult = externalClient.getLocation(riderAreaRequestDtos);
        return (List<AreaDto>) destinationResult.getBody();
    }

    private DistanceResponseDto getDistance(AreaDto startAreaDto, List<AreaDto> riderAreaDtos){

        DistanceRequestDto distanceRequestDto = DistanceRequestDto.create(
            Destination.create(startAreaDto.getX(), startAreaDto.getY(), "start"),
            riderAreaDtos.stream()
                .map(riderArea -> 
                        Origin.create(riderArea.getX(), riderArea.getY(), String.valueOf(riderArea.getKey()))                                        
                )
                .collect(Collectors.toList())
        );

        ResponseEntity<?> distanceResult = externalClient.getDistance(distanceRequestDto);
        return (DistanceResponseDto) distanceResult.getBody();
    }

    private long selectRider(DistanceResponseDto distanceResponseDto){
        int minDuration = Integer.MAX_VALUE;
        String riderId = "0";
        for(DistanceResponseDto.Route route : distanceResponseDto.getRoutes()){
            if(minDuration > route.getSummary().getDuration()){
                riderId = route.getKey();
                minDuration = route.getSummary().getDuration();
            }
        }
        return Long.valueOf(riderId);
    }
}
