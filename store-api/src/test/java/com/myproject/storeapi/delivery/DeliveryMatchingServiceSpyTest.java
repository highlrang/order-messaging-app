package com.myproject.storeapi.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.enums.YesNo;
import com.myproject.core.delivery.domain.DeliveryEntity;
import com.myproject.core.delivery.dto.AreaDto;
import com.myproject.core.delivery.dto.DeliveryDto;
import com.myproject.core.delivery.repository.DeliveryRepository;
import com.myproject.core.order.repository.OrderProductRepository;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.user.repository.RiderRepository;
import com.myproject.storeapi.client.ExternalClient;
import com.myproject.storeapi.common.service.NotificationService;
import com.myproject.storeapi.delivery.dto.DistanceRequestDto;
import com.myproject.storeapi.delivery.dto.DistanceResponseDto;
import com.myproject.storeapi.delivery.dto.DistanceResponseDto.Route;
import com.myproject.storeapi.delivery.dto.DistanceResponseDto.Summary;
import com.myproject.storeapi.delivery.service.DeliveryMatchingService;
import com.myproject.storeapi.delivery.service.DeliveryService;

import com.myproject.core.user.domain.RiderEntity;

@SpringBootTest(args = {"--dbPassword=pfuser1234"})
public class DeliveryMatchingServiceSpyTest {
    
    @MockBean RiderRepository riderRepository;
    @MockBean DeliveryRepository deliveryRepository;
    @MockBean ExternalClient externalClient;
    @MockBean OrderRepository orderRepository;
    @MockBean OrderProductRepository orderProductRepository;
    @MockBean NotificationService notificationService;
    @SpyBean DeliveryMatchingService deliveryMatchingService;
    @Autowired DeliveryService deliveryService;

    @Test
    @DisplayName("주문 라이더 선택 시 배달상태 체크")
    public void matchRiderWithDelivery(){
        
        /* ==============================given================================= */
        long orderId = 1l;
        long matchRiderId = 1l;
        long unmatchRiderId = 2l;
        int matchDuration = 30;
        int unmatchDuration = 30;

        String orderAddress = "용인시 기흥구";
        String orderDetailAddress = "갈곡로 25";
        String matchingRiderArea = orderAddress;
        String matchingRiderNowArea = matchingRiderArea + " " + orderDetailAddress;

        String unMatchingRiderArea = "용인시 수지구";
        RiderEntity givenMatchingRider = RiderEntity.builder().activityArea(matchingRiderArea).deliveringYn(YesNo.N).build();
        givenMatchingRider.setUserId(matchRiderId);
        RiderEntity givenUnmatchingRider = RiderEntity.builder().activityArea(unMatchingRiderArea).deliveringYn(YesNo.Y).build();
        givenUnmatchingRider.setUserId(unmatchRiderId);
        List<RiderEntity> givenRiderEntites = Arrays.asList(givenMatchingRider, givenUnmatchingRider);

        DeliveryEntity givenDelivery = DeliveryEntity.builder().address(orderAddress).detailAddress(orderDetailAddress).build();
        when(deliveryRepository.findByOrderId(anyLong()))
            .thenReturn(Optional.ofNullable(givenDelivery));
        when(riderRepository.findAllByActivityYnAndActivityArea(any(YesNo.class), anyString()))
            .thenReturn(givenRiderEntites);
        
        List<AreaDto> givenRiderAreaDtos = Arrays.asList(AreaDto.builder().x("111").y("112").build());
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
        when(externalClient.getDistance(any(DistanceRequestDto.class)))
            .thenReturn(new ResponseEntity(givenDistanceResponseDto, HttpStatus.OK));

        /* ==============================when================================= */
        DeliveryDto deliveryDto = deliveryService.match(orderId);

        /* ==============================then================================= */
        assertEquals(givenMatchingRider.getUserId(), deliveryDto.getRiderId());
        assertEquals(givenMatchingRider.getActivityArea(), deliveryDto.getAddress());
        
        
    }
}
