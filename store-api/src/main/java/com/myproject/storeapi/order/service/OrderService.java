package com.myproject.storeapi.order.service;


import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.dto.NotificationDto;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.repository.MemberRepository;
import com.myproject.storeapi.delivery.DeliveryMatchingService;
import com.myproject.storeapi.order.producer.NotificationProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final NotificationProducer notificationProducer;
    private final DeliveryMatchingService deliveryMatchingService;
    
    @Transactional
    public OrderDto acceptOrder(long userId, long orderId){
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        orderEntity.changeOrderStatus(OrderStatus.ORDER_ACCEPT, String.valueOf(userId));
        OrderDto orderDto = new OrderDto(orderEntity);

        try{
            sendMessage(orderDto);
            deliveryMatchingService.match(orderDto.getOrderId());

        }catch(Exception e){
            log.error("[KAFKA SEND FAILED]");
            log.error(e.toString() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        
        return orderDto;
    }

    @Transactional
    public OrderDto rejectOrder(long userId, long orderId){
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        
        orderEntity.changeOrderStatus(OrderStatus.ORDER_REJECT, String.valueOf(userId));
        OrderDto orderDto = new OrderDto(orderEntity);

        try{
            sendMessage(orderDto);

        }catch(Exception e){
            log.error("[KAFKA SEND FAILED]");
            log.error(e.toString() + "\n" + Arrays.toString(e.getStackTrace()));
        }

        return orderDto;
    }

    private void sendMessage(OrderDto orderDto) throws Exception{
        
        MemberEntity memberEntity = memberRepository.findById(orderDto.getMemberId())
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        NotificationDto notificationDto = NotificationDto.builder()
                .orderStatus(OrderStatus.of(orderDto.getOrderStatus()))
                .orderId(orderDto.getOrderId())
                .orderName(orderDto.getOrderName())
                .phoneNumbers(Arrays.asList(memberEntity.getPhoneNumber()))
                .build();

        // TODO 중복 코드라서 하나로 모을 필요
        notificationProducer.send(notificationDto);
    }
}
