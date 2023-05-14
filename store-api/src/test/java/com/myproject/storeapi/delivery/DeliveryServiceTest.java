package com.myproject.storeapi.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.delivery.domain.DeliveryEntity;
import com.myproject.core.delivery.dto.DeliveryDto;
import com.myproject.core.delivery.repository.DeliveryRepository;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.repository.OrderProductRepository;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.user.repository.RiderRepository;
import com.myproject.storeapi.common.service.NotificationService;
import com.myproject.storeapi.delivery.service.DeliveryMatchingService;
import com.myproject.storeapi.delivery.service.DeliveryService;
import com.myproject.storeapi.order.service.OrderService;

@ExtendWith(SpringExtension.class)
class DeliveryServiceTest {

    @Mock OrderRepository orderRepository;
    @Mock DeliveryRepository deliveryRepository;
    @Mock RiderRepository riderRepository;
    @Mock OrderProductRepository orderProductRepository;
    @Mock NotificationService notificationService;
    @InjectMocks DeliveryService deliveryService;

    @Test
    @DisplayName("라이더 배달 시작")
    public void startDelivery(){
        
        /* =========================== GIVEN ================================ */
        long orderId = 1l;
        long riderId = 1l;
        OrderStatus beforeStatus = OrderStatus.ORDER_ACCEPT;
        OrderStatus afterStatus = OrderStatus.DELIVERY_START;
        OrderEntity givenOrderEntity = OrderEntity.builder().orderStatus(beforeStatus.getStatus()).build();
        DeliveryEntity givenDeliveryEntity = DeliveryEntity.builder().riderId(riderId).build();
        when(orderRepository.findById(anyLong()))
            .thenReturn(Optional.ofNullable(givenOrderEntity));
        when(deliveryRepository.findByOrderId(anyLong()))
            .thenReturn(Optional.ofNullable(givenDeliveryEntity));
        when(orderProductRepository.findByOrderId(anyLong()))
            .thenReturn(new ArrayList<>());
        doNothing().when(notificationService).sendMessage(any());

        /* =========================== WHEN ================================ */
        OrderDto orderDto = deliveryService.startDelivery(orderId);

        /* =========================== THEN ================================ */
        assertEquals(afterStatus.getStatus(), orderDto.getOrderStatus());
    }

    @Test
    @DisplayName("배달 완료 TDD")
    public void deliveryComplete(){

        /* ============================== GIVEN =============================== */
        long orderId = 1l;
        OrderEntity givenOrderEntity = OrderEntity.builder().orderStatus(OrderStatus.DELIVERY_START.getStatus()).build();
        DeliveryEntity givenDeliveryEntity = DeliveryEntity.builder().build();
        when(orderRepository.findById(anyLong()))
            .thenReturn(Optional.ofNullable(givenOrderEntity));
        when(deliveryRepository.findByOrderId(anyLong()))
            .thenReturn(Optional.ofNullable(givenDeliveryEntity));
        when(orderProductRepository.findByOrderId(anyLong()))
            .thenReturn(new ArrayList<>());
        doNothing().when(notificationService).sendMessage(any());

        /* ============================== WHEN =============================== */
        OrderDto orderDto = deliveryService.completeDelivery(orderId);

        /* ============================== THEN =============================== */
        assertEquals(OrderStatus.DELIVERY_COMPLETE.getStatus(), orderDto.getOrderStatus());

    }
}