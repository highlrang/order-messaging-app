package com.myproject.storeapi.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.dto.ApiResponse;
import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.enums.YesNo;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.delivery.domain.DeliveryEntity;
import com.myproject.core.delivery.domain.DeliveryHistoryEntity;
import com.myproject.core.delivery.dto.RiderAreaDto;
import com.myproject.core.delivery.repository.DeliveryHistoryRepository;
import com.myproject.core.delivery.repository.DeliveryRepository;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.domain.RiderEntity;
import com.myproject.core.user.repository.MemberRepository;
import com.myproject.core.user.repository.RiderRepository;
import com.myproject.storeapi.client.ExternalClient;

import lombok.Getter;

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
        String orderAddress = "용인시 기흥구";
        String matchingRiderArea = orderAddress;

        String unMatchingRiderArea = "용인시 수지구";
        RiderEntity givenMatchingRider = RiderEntity.builder().activityArea(matchingRiderArea).build();
        RiderEntity givenUnmatchingRider = RiderEntity.builder().activityArea(unMatchingRiderArea).build();

        DeliveryEntity givenDelivery = DeliveryEntity.builder().address(orderAddress).build();
        when(deliveryRepository.findByOrderId(anyLong()))
            .thenReturn(Optional.ofNullable(givenDelivery));

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

            return new DeliveryDto();
        }
    }

    private RiderEntity selectDeliveryRiderByDistance(List<RiderEntity> riderEntities){
        
        RiderEntity selectedRider;

        List<RiderAreaDto> riderAreaRequestDtos = riderEntities.stream()
            .map(rider -> RiderAreaDto.builder().riderId(rider.getUserId()).address(rider.getNowArea()).build())
            .collect(Collectors.toList());
        
        ResponseEntity<ApiResponse<?>> locationResult = externalClient.getMapLocation(riderAreaRequestDtos);
        List<RiderAreaDto> riderAreaResponseDtos = (List<RiderAreaDto>) locationResult.getBody().getData();

        ResponseEntity<ApiResponse<?>> distanceResult = externalClient.getDistance(riderAreaResponseDtos);
        

        return new RiderEntity();
    }
}
