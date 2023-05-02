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
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.domain.OrderProductEntity;
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
        OrderService orderService = new OrderService();
        OrderProductDto orderProductOneDto = new OrderProductDto(productOneId, orderOnePrice, productOneQuantity);
        OrderProductDto orderProductTwoDto = new OrderProductDto(productTwoId, orderTwoPrice, productTwoQuantity);
        OrderRequestDto orderRequestDto = new OrderRequestDto(memberId, Arrays.asList(orderProductOneDto, orderProductTwoDto));
        OrderCollectionDto orderCollectionDto = orderService.createOrder(orderRequestDto);
        OrderResponseDto orderResponseDto = orderCollectionDto.getOrderDto();
        List<OrderProductDto> orderProductDtoList = orderCollectionDto.getOrderProductDtos();
        

        /* ========================= THEN ============================ */
        assertNotEquals(orderResponseDto.getOrderId(), 0);
        assertEquals(givenOrderEntity.getOrderPrice(), orderResponseDto.getOrderPrice());
        assertEquals(orderProductEntites.size(), orderProductDtoList.size());
        
        for(int i=0; i<orderProductDtoList.size(); i++){
            assertEquals(orderProductEntites.get(i).getProductId(), orderProductDtoList.get(i).getProductId());
            assertEquals(orderProductEntites.get(i).getOrderQuantity(), orderProductDtoList.get(i).getOrderQuantity());
            assertEquals(orderProductEntites.get(i).getOrderPrice(), orderProductDtoList.get(i).getOrderPrice());
            
        }

    }

    @Getter
    @AllArgsConstructor
    class OrderRequestDto{
        private long memberId;
        private List<OrderProductDto> orderProductList;

    }

    @Getter
    @AllArgsConstructor
    class OrderProductDto{
        private long productId;
        private int orderPrice;
        private int orderQuantity;

        public OrderProductDto(OrderProductEntity e){
            this.productId = e.getProductId();
            this.orderPrice = e.getOrderPrice();
            this.orderQuantity = e.getOrderQuantity();
        }
    }

    @Getter
    class OrderResponseDto {
        private long orderId;
        private long memberId;
        private String orderName;
        private int orderPrice;
        private int orderStatus;
        

        public OrderResponseDto(OrderEntity e){
            this.orderId = e.getOrderId();
            this.memberId = e.getMemberId();
            this.orderName = e.getOrderName();
            this.orderPrice = e.getOrderPrice();
            this.orderStatus = e.getOrderStatus();
        }
    }

    @Getter
    @AllArgsConstructor
    class OrderCollectionDto {
        private OrderResponseDto orderDto;
        private List<OrderProductDto> orderProductDtos;

    }

    class OrderService {

        public OrderService(){}

        public OrderCollectionDto createOrder(OrderRequestDto orderRequestDto){

            long orderId = 1l;
            OrderEntity orderEntity = OrderEntity.builder()
                .orderId(orderId)
                .memberId(orderRequestDto.getMemberId())
                .build();
            OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

            List<OrderProductEntity> orderProductEntityList = createOrderProduct(orderId, orderRequestDto);

            String orderName = orderProductEntityList.size() > 1 ?
                orderProductEntityList.get(0).getProductName() + " 외 " + orderProductEntityList.size() + "건" :
                orderProductEntityList.get(0).getProductName();
            int orderPrice = orderProductEntityList.stream()
                .mapToInt(ope -> ope.getOrderPrice())
                .sum();
            List<OrderProductEntity> orderProductEntities = orderProductRepository.saveAll(orderProductEntityList);
            List<OrderProductDto> orderProductDtos = orderProductEntities.stream()
                .map(OrderProductDto::new)
                .collect(Collectors.toList());
            savedOrderEntity.setOrderInfo(orderName, orderPrice);
            
            return new OrderCollectionDto(
                new OrderResponseDto(savedOrderEntity),
                orderProductDtos
                );
            
        }

        public List<OrderProductEntity> createOrderProduct(long orderId, OrderRequestDto orderRequestDto){
            long orderProductId = 1l;
            List<OrderProductEntity> orderProductEntityList = new ArrayList<>();
            for(OrderProductDto orderProductDto: orderRequestDto.getOrderProductList()){
                ProductEntity productEntity = productRepository.findById(orderProductDto.getProductId())
                    .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

                OrderProductEntity orderProductEntity = OrderProductEntity.builder()
                        .orderProductId(orderProductId)
                        .orderId(orderId)
                        .productId(orderProductDto.getProductId())
                        .productName(productEntity.getProductName())
                        .orderQuantity(orderProductDto.getOrderQuantity())
                        .orderPrice(orderProductDto.getOrderPrice())
                        .build();
                
                orderProductEntityList.add(orderProductEntity);    
                orderProductId ++;
            }
            return orderProductEntityList;
        }
    }
}
