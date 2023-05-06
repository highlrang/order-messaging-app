package com.myproject.storeapi.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.dto.ApiResponse;
import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.enums.YesNo;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.delivery.domain.DeliveryEntity;
import com.myproject.core.delivery.dto.RiderAreaDto;
import com.myproject.core.delivery.repository.DeliveryRepository;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.domain.RiderEntity;
import com.myproject.core.user.repository.MemberRepository;
import com.myproject.core.user.repository.RiderRepository;
import com.myproject.storeapi.client.ExternalClient;
import com.myproject.storeapi.delivery.dto.DistanceResponseDto;
import com.myproject.storeapi.delivery.dto.DistanceResponseDto.Route;
import com.myproject.storeapi.delivery.dto.DistanceResponseDto.Summary;

import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import lombok.Getter;
import scala.compat.java8.functionConverterImpls.RichDoubleConsumerAsFunction1;

@ExtendWith(SpringExtension.class)
public class DeliveryMatchingTest {

    @Mock RiderRepository riderRepository;
    @Mock DeliveryRepository deliveryRepository;
    @Mock ExternalClient externalClient;

    @Test
    @DisplayName("주문 라이더 배차 기능 TDD")
    public void matchRider(){
        
        /* ==============================given================================= */
        long orderId = 1l;
        long memberId = 1l;
        long matchRiderId = 1l;
        long unmatchRiderId = 2l;
        int matchDuration = 10;
        int unmatchDuration = 30;

        String orderAddress = "용인시 기흥구";
        String matchingRiderArea = orderAddress;

        String unMatchingRiderArea = "용인시 수지구";
        RiderEntity givenMatchingRider = RiderEntity.builder().activityArea(matchingRiderArea).build();
        givenMatchingRider.setUserId(matchRiderId);
        RiderEntity givenUnmatchingRider = RiderEntity.builder().activityArea(unMatchingRiderArea).build();
        givenUnmatchingRider.setUserId(unmatchRiderId);
        List<RiderEntity> givenRiderEntites = Arrays.asList(givenMatchingRider, givenUnmatchingRider);

        DeliveryEntity givenDelivery = DeliveryEntity.builder().address(orderAddress).build();
        when(deliveryRepository.findByOrderId(anyLong()))
            .thenReturn(Optional.ofNullable(givenDelivery));
        when(riderRepository.findAllByActivityYnAndActivityArea(any(YesNo.class), anyString()))
            .thenReturn(givenRiderEntites);
        
        List<RiderAreaDto> givenRiderAreaDtos = Arrays.asList(RiderAreaDto.builder().build());
        DistanceResponseDto givenDistanceResponseDto = DistanceResponseDto.builder()
            .routes(
                Arrays.asList(
                            Route.builder()
                                    .key(String.valueOf(matchRiderId))
                                    .summary(Summary.builder().duration(matchDuration).build())
                                    .build(), 
                            Route.builder()
                                    .key(String.valueOf(unmatchRiderId))
                                    .summary(Summary.builder().duration(unmatchDuration).build())
                                    .build()
                            )
            )
            .build();
        
        when(externalClient.getLocation(anyList()))
            .thenReturn(new ResponseEntity(givenRiderAreaDtos, HttpStatus.OK));
        when(externalClient.getDistance(anyList()))
            .thenReturn(new ResponseEntity(givenDistanceResponseDto, HttpStatus.OK));
        

        /* ==============================when================================= */
        DeliveryMatchingService deliveryMatchingService = new DeliveryMatchingService();
        DeliveryDto deliveryDto = deliveryMatchingService.match(orderId);

        /* ==============================then================================= */
        assertEquals(givenMatchingRider.getUserId(), deliveryDto.getRiderId());
        assertEquals(matchingRiderArea, deliveryDto.getAddress());
        
    }

    @Getter
    class DeliveryDto {

        private long riderId;
        private String address;

        public DeliveryDto(DeliveryEntity e){
            this.riderId = e.getRiderId();
            this.address = e.getAddress();
        }
    }

    class DeliveryMatchingService{

        public DeliveryDto match(long orderId){

            // 1. 주문한 주소 필요
            DeliveryEntity deliveryEntity = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

            // 2. 주소에 해당하는 라이더 select
            String deliveryAddress = deliveryEntity.getAddress();
            List<RiderEntity> riderEntites = riderRepository.findAllByActivityYnAndActivityArea(YesNo.Y, deliveryAddress);
            RiderEntity riderEntity = selectDeliveryRiderByDistance(riderEntites);


            // 3. 비교하여 delivery와 delivery_history에 저장
            deliveryEntity.setRider(riderEntity.getUserId());
            riderEntity.setActivityYn(YesNo.Y);

            return new DeliveryDto(deliveryEntity);
        }
    }

    private RiderEntity selectDeliveryRiderByDistance(List<RiderEntity> riderEntities){

        List<RiderAreaDto> riderAreaRequestDtos = riderEntities.stream()
            .map(rider -> RiderAreaDto.builder().riderId(rider.getUserId()).address(rider.getNowArea()).build())
            .collect(Collectors.toList());
        
        ResponseEntity<?> locationResult = externalClient.getLocation(riderAreaRequestDtos);
        List<RiderAreaDto> riderAreaResponseDtos = (List<RiderAreaDto>) locationResult.getBody();

        ResponseEntity<?> distanceResult = externalClient.getDistance(riderAreaResponseDtos);
        DistanceResponseDto distanceResponseDto = (DistanceResponseDto) distanceResult.getBody();
        
        int minDuration = Integer.MAX_VALUE;
        String riderId = "";
        for(DistanceResponseDto.Route route : distanceResponseDto.getRoutes()){
            if(minDuration > route.getSummary().getDuration()){
                riderId = route.getKey();
                minDuration = route.getSummary().getDuration();
            }
        }

        System.out.println("\nhere = " + riderId);
        for(RiderEntity rider: riderEntities){
            System.out.println("\nhere = " + rider.getUserId());
        }
        Long selectedRiderId = Long.valueOf(riderId);
        return riderEntities.stream()
            .filter(rider -> rider.getUserId() == selectedRiderId)
            .findAny()
            .orElseThrow(() -> new CustomException(ErrorCode.RIDER_MATCHING_ERROR));
    }

    
}
