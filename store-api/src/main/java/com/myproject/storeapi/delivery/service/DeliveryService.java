package com.myproject.storeapi.delivery.service;



import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.enums.YesNo;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.delivery.domain.DeliveryEntity;
import com.myproject.core.delivery.dto.DeliveryDto;
import com.myproject.core.delivery.repository.DeliveryRepository;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.domain.OrderProductEntity;
import com.myproject.core.order.dto.NotificationDto;
import com.myproject.core.order.dto.OrderCollectionDto;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.dto.OrderProductDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.repository.OrderProductRepository;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.user.domain.RiderEntity;
import com.myproject.core.user.repository.RiderRepository;
import com.myproject.storeapi.common.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    
    private final DeliveryRepository deliveryRepository;
    private final RiderRepository riderRepository;
    private final DeliveryMatchingService deliveryMatchingService;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final NotificationService notificationService;

 
    @Transactional
    public DeliveryDto match(long orderId){

        // 1. 주문한 주소 필요
        DeliveryEntity deliveryEntity = deliveryRepository.findByOrderId(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        // 2. 주소에 해당하는 라이더 select
        String deliveryAddr = deliveryEntity.getAddress();
        String deliveryFullAddr = deliveryAddr + " " + deliveryEntity.getDetailAddress();
        List<RiderEntity> riderEntites = riderRepository.findAllByActivityYnAndActivityArea(YesNo.Y, deliveryAddr);

        // 2-1. 매칭 서비스 호출
        RiderEntity riderEntity = deliveryMatchingService.selectDeliveryRiderByDistance(riderEntites, deliveryFullAddr);

        // 3. delivery & rider 업데이트
        deliveryEntity.setRider(riderEntity.getUserId());
        riderEntity.setDeliveringYn(YesNo.Y);

        return new DeliveryDto(deliveryEntity);
    }

    @Transactional
    public OrderDto startDelivery(long orderId) {
        
        /* ================================ DELIVERY ================================ */
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        DeliveryEntity deliveryEntity = deliveryRepository.findByOrderId(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        if(orderEntity.getOrderStatus() != OrderStatus.ORDER_ACCEPT.getStatus())
            throw new CustomException(ErrorCode.INVALID_ORDER_STATUS);

        orderEntity.changeOrderStatus(OrderStatus.DELIVERY_START, String.valueOf(deliveryEntity.getRiderId())); 

        OrderDto orderDto = new OrderDto(orderEntity);

        /* ================================ KAFKA MESSAGE ================================ */
        
        sendMessage(orderDto);

        return orderDto;
        
    }

    @Transactional
    public OrderDto completeDelivery(long orderId){

        /* ================================ DELIVERY ================================ */
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        DeliveryEntity deliveryEntity = deliveryRepository.findByOrderId(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        if(orderEntity.getOrderStatus() != OrderStatus.DELIVERY_START.getStatus())
            throw new CustomException(ErrorCode.INVALID_ORDER_STATUS);
        orderEntity.changeOrderStatus(OrderStatus.DELIVERY_COMPLETE, String.valueOf(deliveryEntity.getRiderId()));
        
        OrderDto orderDto = new OrderDto(orderEntity);

        /* ================================ KAFKA MESSAGE ================================ */

        sendMessage(orderDto);

        return orderDto;

    }

    private void sendMessage(OrderDto orderDto){
        List<OrderProductEntity> orderProductEntites = orderProductRepository.findByOrderId(orderDto.getOrderId());
        
        List<OrderProductDto> orderProductDtos = orderProductEntites.stream().map(OrderProductDto::new).collect(Collectors.toList());
        OrderCollectionDto orderCollectionDto = new OrderCollectionDto(orderDto, orderProductDtos);
        notificationService.sendMessage(orderCollectionDto);
    }
}
