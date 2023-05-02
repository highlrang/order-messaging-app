package com.myproject.orderapi.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.domain.OrderProductEntity;
import com.myproject.core.order.dto.OrderCollectionDto;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.dto.OrderProductDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.repository.OrderProductRepository;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.product.domain.ProductEntity;
import com.myproject.core.product.repository.ProductRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @Mock ProductRepository productRepository;
    @Mock OrderRepository orderRepository;
    @Mock OrderProductRepository orderProductRepository;
    @InjectMocks OrderService orderService;
    
    @Test
    @DisplayName("회원의 주문 완료")
    public void orderCompleteTest(){
        
        /* ======================== GIVEN ======================== */
        long orderId = 1l;
        long memberId = 1l;
        
        long productOneId = 1l;
        long productTwoId = 2l;
        String productOneName = "상품1";
        String productTwoName = "상품2";
        ProductEntity productOneEntity = ProductEntity.builder()
            .productId(productOneId)
            .productName(productOneName)
            .build();
        ProductEntity productTwoEntity = ProductEntity.builder()
            .productId(productTwoId)
            .productName(productTwoName)
            .build();

        int productOnePrice = 9000; 
        int productTwoPrice = 23000;
        int productOneQuantity = 2;
        int productTwoQuantity = 1;
        int orderOnePrice = productOnePrice * productOneQuantity;
        int orderTwoPrice = productTwoPrice * productTwoQuantity;
        
        int orderProductCount = 2;
        String orderName = orderProductCount > 1 ? productOneName : productOneName + " 외 " + orderProductCount + "건";
        int totalOrderPrice = orderOnePrice + orderTwoPrice;
        int orderStatus = OrderStatus.ORDER_COMPLETE.getStatus();

        OrderEntity givenOrderEntity = OrderEntity.builder()
            .orderId(orderId)
            .memberId(memberId)
            .orderName(orderName)
            .orderPrice(totalOrderPrice)
            .orderStatus(orderStatus)
            .build();
        
        long orderProductOneId = 1l;
        long orderProductTwoId = 2l;
        OrderProductEntity givenOrderProduct1 = OrderProductEntity.builder()
            .orderProductId(orderProductOneId)
            .orderId(orderId)
            .orderPrice(orderOnePrice)
            .orderQuantity(productOneQuantity)
            .build();
        OrderProductEntity givenOrderProduct2 = OrderProductEntity.builder()
            .orderProductId(orderProductTwoId)
            .orderId(orderId)
            .orderPrice(orderTwoPrice)
            .orderQuantity(productTwoQuantity)
            .build();
        List<OrderProductEntity> orderProductEntites = Arrays.asList(givenOrderProduct1, givenOrderProduct2);

        when(orderRepository.save(any(OrderEntity.class)))
            .thenReturn(givenOrderEntity);
        when(productRepository.findById(productOneId))
            .thenReturn(Optional.ofNullable(productOneEntity));
        when(productRepository.findById(productTwoId))
            .thenReturn(Optional.ofNullable(productTwoEntity));
        when(orderProductRepository.saveAll(any()))
            .thenReturn(orderProductEntites);

        /* ========================= WHEN ============================ */
        OrderProductDto orderProductOneDto = new OrderProductDto(productOneId, orderOnePrice, productOneQuantity);
        OrderProductDto orderProductTwoDto = new OrderProductDto(productTwoId, orderTwoPrice, productTwoQuantity);
        List<OrderProductDto> orderProductDtos = Arrays.asList(orderProductOneDto, orderProductTwoDto);

        OrderCollectionDto orderCollectionDto = orderService.createOrder(memberId, orderProductDtos);
        
        OrderDto orderDto = orderCollectionDto.getOrderDto();
        List<OrderProductDto> orderProductDtoList = orderCollectionDto.getOrderProductDtos();
        

        /* ========================= THEN ============================ */
        assertNotEquals(orderDto.getOrderId(), 0);
        assertEquals(givenOrderEntity.getOrderPrice(), orderDto.getOrderPrice());
        assertEquals(orderProductEntites.size(), orderProductDtoList.size());
        
        for(int i=0; i<orderProductDtoList.size(); i++){
            assertEquals(orderProductEntites.get(i).getProductId(), orderProductDtoList.get(i).getProductId());
            assertEquals(orderProductEntites.get(i).getOrderQuantity(), orderProductDtoList.get(i).getOrderQuantity());
            assertEquals(orderProductEntites.get(i).getOrderPrice(), orderProductDtoList.get(i).getOrderPrice());
            
        }

    }

}
