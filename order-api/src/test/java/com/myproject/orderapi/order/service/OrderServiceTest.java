package com.myproject.orderapi.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.repository.OrderRepository;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @Mock OrderRepository orderRepository;
    
    @Test
    @DisplayName("회원의 주문 완료")
    public void orderCompleteTest(){
        
        /* ======================== GIVEN ======================== */
        OrderEntity givenOrderEntity = OrderEntity.builder().build();
        when(orderRepository.save(any(OrderEntity.class)))
            .thenReturn(givenOrderEntity);


        /* ========================= WHEN ============================ */
        OrderEntity orderCreateEntity = OrderEntity.builder().build();
        OrderEntity savedOrderEntity = orderRepository.save(orderCreateEntity);


        /* ========================= THEN ============================ */
        assertNotEquals(savedOrderEntity.getOrderId(), 0);
        assertEquals(givenOrderEntity.getProductId(), savedOrderEntity.getProductId());
        assertEquals(givenOrderEntity.getOrderPrice(), savedOrderEntity.getOrderPrice());
        assertEquals(givenOrderEntity.getOrderQuantity(), savedOrderEntity.getOrderQuantity());

    }
}
