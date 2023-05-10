package com.myproject.storeapi.delivery.service;



import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.enums.YesNo;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.delivery.domain.DeliveryEntity;
import com.myproject.core.delivery.dto.DeliveryDto;
import com.myproject.core.delivery.repository.DeliveryRepository;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.user.domain.RiderEntity;
import com.myproject.core.user.repository.RiderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    
    private final DeliveryRepository deliveryRepository;
    private final RiderRepository riderRepository;
    private final DeliveryMatchingService deliveryMatchingService;
    private final OrderRepository orderRepository;

 
    @Transactional
    public DeliveryDto match(long orderId){

        // 1. 주문한 주소 필요
        DeliveryEntity deliveryEntity = deliveryRepository.findByOrderId(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        // 2. 주소에 해당하는 라이더 select
        String deliveryAddr = deliveryEntity.getAddress();
        String deliveryFullAddr = deliveryAddr + " " + deliveryEntity.getDetailAddress();
        List<RiderEntity> riderEntites = riderRepository.findAllByActivityYnAndActivityArea(YesNo.Y, deliveryAddr);
        RiderEntity riderEntity = deliveryMatchingService.selectDeliveryRiderByDistance(riderEntites, deliveryFullAddr);


        // 3. 비교하여 delivery & rider 업데이트
        deliveryEntity.setRider(riderEntity.getUserId());
        riderEntity.setDeliveringYn(YesNo.Y);

        return new DeliveryDto(deliveryEntity);
    }

    @Transactional
    public OrderDto startDelivery(long orderId) {
            
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        DeliveryEntity deliveryEntity = deliveryRepository.findByOrderId(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        if(orderEntity.getOrderStatus() != OrderStatus.ORDER_ACCEPT.getStatus())
            throw new CustomException(ErrorCode.INVALID_ORDER_STATUS);

        orderEntity.changeOrderStatus(OrderStatus.DELIVERY_START, String.valueOf(deliveryEntity.getRiderId()));

        return new OrderDto(orderEntity);
        
    }
}
