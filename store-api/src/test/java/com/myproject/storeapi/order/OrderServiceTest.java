package com.myproject.storeapi.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.storeapi.order.service.OrderService;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @Mock OrderRepository orderRepository;
    @InjectMocks OrderService orderService;

    @Test
    @DisplayName("주문 승인 기능 생성")
    public void acceptOrder(){

        /* ============================ given ================================= */
        long userId = 1l;
        long orderId = 1l;
        OrderStatus beforeStatus = OrderStatus.ORDER_COMPLETE;
        OrderStatus afterStatus = OrderStatus.ORDER_ACCEPT;

        OrderEntity givenEntity = OrderEntity.builder().orderStatus(beforeStatus.getStatus()).build();
        when(orderRepository.findById(anyLong()))
            .thenReturn(Optional.ofNullable(givenEntity));
        
        /* ============================ when ================================= */
        OrderDto updatedOrderStatus = orderService.acceptOrder(userId, orderId);

        /* ============================ then ================================= */
        assertEquals(afterStatus.getStatus(), updatedOrderStatus.getOrderStatus());
    }

    @Test
    @DisplayName("주문 거절 기능 생성")
    public void rejectOrder(){
        
        /* ============================ given ================================= */
        long userId = 1l;
        long orderId = 1l;
        OrderStatus beforeStatus = OrderStatus.ORDER_COMPLETE;
        OrderStatus afterStatus = OrderStatus.ORDER_REJECT;
        OrderEntity givenOrderEntity = OrderEntity.builder().orderStatus(beforeStatus.getStatus()).build();
        when(orderRepository.findById(anyLong()))
            .thenReturn(Optional.ofNullable(givenOrderEntity));

        /* ============================ when ================================= */
        OrderDto updatedOrderStatus = orderService.rejectOrder(userId, orderId);
        
        /* ============================ then ================================= */
        assertEquals(afterStatus.getStatus(), updatedOrderStatus.getOrderStatus());
    }
}
