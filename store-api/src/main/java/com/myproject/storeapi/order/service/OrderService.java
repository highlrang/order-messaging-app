package com.myproject.storeapi.order.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    
    @Transactional
    public OrderDto acceptOrder(long userId, long orderId){
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        orderEntity.changeOrderStatus(OrderStatus.ORDER_ACCEPT, String.valueOf(userId));
        
        return new OrderDto(orderEntity);
    }

    @Transactional
    public OrderDto rejectOrder(long userId, long orderId){
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        
        orderEntity.changeOrderStatus(OrderStatus.ORDER_REJECT, String.valueOf(userId));

        return new OrderDto(orderEntity);
    }
}
