package com.myproject.storeapi.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.repository.OrderRepository;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @Mock OrderRepository orderRepository;

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
        OrderService orderService = new OrderService();
        OrderDto updatedOrderStatus = orderService.acceptOrder(userId, orderId);

        /* ============================ then ================================= */
        assertEquals(afterStatus.getStatus(), updatedOrderStatus.getOrderStatus());
    }

    @Test
    @DisplayName("주문 거절 기능 생성")
    public void rejectOrder(long orderId){
    
    }

    class OrderService {

        public OrderDto acceptOrder(long userId, long orderId){
            OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

            orderEntity.changeOrderStatus(OrderStatus.ORDER_ACCEPT, String.valueOf(userId));
            
            return new OrderDto(orderEntity);
        }
    }
}
