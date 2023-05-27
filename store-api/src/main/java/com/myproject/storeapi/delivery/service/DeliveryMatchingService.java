package com.myproject.storeapi.delivery.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.enums.YesNo;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.delivery.dto.AreaDto;
import com.myproject.core.user.domain.RiderEntity;
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

    private final ExternalClient externalClient;

    /**
     * 거리 / 배달상태 / 페널티를 기반으로 라이더 선택
     * @param riderEntities
     * @param deliveryAddress
     * @return
     */
    public RiderEntity selectDeliveryRiderByDistance(List<RiderEntity> riderEntities, String deliveryAddress){

        // 라이더들의 현재 위치 좌표를 얻어온다.
        List<AreaDto> riderAreaResponseDtos = getRiderAreaDtos(riderEntities);
        // 목적지의 위치 좌표를 얻어온다.
        AreaDto startAreaDto = getStartAreaDto(deliveryAddress);

        // 거리를 얻어온다.
        DistanceResponseDto distanceResponseDto = getDistance(startAreaDto, riderAreaResponseDtos);

        // 거리 | 배달상태 | 페널티를 기반으로 라이더를 선택한다.
        RiderEntity riderEntity = selectRider(riderEntities, distanceResponseDto);

        return riderEntity;

    }

    private List<AreaDto> getRiderAreaDtos(List<RiderEntity> riderEntities){
        List<AreaDto> riderAreaRequestDtos = riderEntities.stream()
            .map(rider -> new AreaDto(rider.getNowArea(), rider.getUserId()))
            .collect(Collectors.toList());

        ResponseEntity<?> destinationResult = externalClient.getLocation(riderAreaRequestDtos);
        return (List<AreaDto>) destinationResult.getBody();
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

    public RiderEntity selectRider(List<RiderEntity> riderEntities, DistanceResponseDto distanceResponseDto){
        int minDuration = Integer.MAX_VALUE;
        String riderId = "0";

        List<String> excludeRiderIds = new ArrayList<>();
        RiderEntity selectedRider;

        while(true){
            // 모두 적합하지 않은 라이더일 경우 첫 번째 라이더 선택
            if(excludeRiderIds.size() == riderEntities.size()){
                riderId = excludeRiderIds.get(0);
                selectedRider = selectRiderEntity(riderId, riderEntities);
                break;
            }

            for(DistanceResponseDto.Route route : distanceResponseDto.getRoutes()){
            
                // for-loop 가장 작은 거리를 가진 라이더 선택
                if(!excludeRiderIds.contains(route.getKey()) && minDuration > route.getSummary().getDuration())
                    riderId = route.getKey();
                    minDuration = route.getSummary().getDuration();
            }

            RiderEntity riderEntity = selectRiderEntity(riderId, riderEntities);

            // 라이더 제외 처리
            if(riderEntity.getPenalty() > 5 || riderEntity.getDeliveringYn().name().equals(YesNo.Y.name())){
                excludeRiderIds.add(String.valueOf(riderEntity.getUserId()));
                continue;
            }

            // 라이더 선택 후 종료
            selectedRider = riderEntity;
            break;
            
        }

        return selectedRider;
        
    }

    private RiderEntity selectRiderEntity(String riderId, List<RiderEntity> riderEntities){
        long selectedRiderId = Long.valueOf(riderId);
        RiderEntity riderEntity = riderEntities.stream()
            .filter(rider -> rider.getUserId() == selectedRiderId)
            .findAny()
            .orElseThrow(() -> new CustomException(ErrorCode.RIDER_MATCHING_ERROR));
        return riderEntity;
    }
    
}
