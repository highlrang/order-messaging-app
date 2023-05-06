package com.myproject.storeapi.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.exception.CustomException;
import com.myproject.core.delivery.domain.DeliveryHistoryEntity;
import com.myproject.core.delivery.repository.DeliveryHistoryRepository;
import com.myproject.core.delivery.repository.DeliveryRepository;
import com.myproject.core.user.domain.RiderEntity;
import com.myproject.core.user.repository.RiderRepository;

import lombok.Getter;

@ExtendWith(SpringExtension.class)
public class DeliveryMatchingTest {
    
    @Mock RiderRepository riderRepository;
    @Mock DeliveryRepository deliveryRepository;
    @Mock DeliveryHistoryRepository deliveryHistoryRepository;

    @Test
    @DisplayName("주문 라이더 배차 기능 TDD")
    public void matchRider(){
        
        /* ==============================given================================= */
        long orderId = 1l;
        long memberId = 1l;
        String memberAddress = "용인시 기흥구";
        String matchingRiderArea = memberAddress;
        String unMatchingRiderArea = "용인시 수지구";
        RiderEntity givenMatchingRider = RiderEntity.builder().activityArea(matchingRiderArea).build();
        RiderEntity givenUnmatchingRider = RiderEntity.builder().activityArea(unMatchingRiderArea).build();

        /* ==============================when================================= */
        DeliveryMatchingService deliveryMatchingService = new DeliveryMatchingService();
        DeliveryDto deliveryDto = deliveryMatchingService.match(orderId, memberId);

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

        public DeliveryDto match(long orderId, long memberId){

            // 1. 주문한 회원의 주소 필요
                

            // 2. 주소에 해당하는 라이더 select


            // 3. 비교하여 delivery와 delivery_history에 저장

            return new DeliveryDto();
        }
    }
}
