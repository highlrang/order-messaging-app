package com.myproject.orderapi.order.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.delivery.domain.DeliveryEntity;
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
import com.myproject.core.product.domain.ProductEntity;
import com.myproject.core.product.repository.ProductRepository;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.domain.StoreEntity;
import com.myproject.core.user.repository.MemberRepository;
import com.myproject.core.user.repository.StoreRepository;
import com.myproject.orderapi.common.service.NotificationService;
import com.myproject.orderapi.order.producer.NotificationProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    @Transactional
    public OrderCollectionDto createOrder(long userId, List<OrderProductDto> orderProductDtos){

        MemberEntity memberEntity = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        /* ================================= ORDER ==================================== */
        OrderEntity savedOrderEntity = orderRepository.save(new OrderEntity(userId));
        long orderId = savedOrderEntity.getOrderId();

        /* ============================== ORDER_PRODUCT ================================ */
        List<OrderProductEntity> orderProductEntites = createOrderProduct(orderId, orderProductDtos);
        List<OrderProductEntity> savedOrderProductEntities = orderProductRepository.saveAll(orderProductEntites);
        List<OrderProductDto> orderProductDtoList = savedOrderProductEntities.stream()
                                            .map(OrderProductDto::new)
                                            .collect(Collectors.toList());
        
        /* ================================= ORDER ==================================== */
        String orderName = orderProductDtoList.size() > 1 ?
                            orderProductDtoList.get(0).getProductName() + " 외 " + orderProductEntites.size() + "건" :
                            orderProductDtoList.get(0).getProductName();
                            int orderPrice = orderProductEntites.stream()
                                .mapToInt(ope -> ope.getOrderPrice())
                                .sum();
        savedOrderEntity.setOrderInfo(orderName, orderPrice);
        OrderDto orderDto = new OrderDto(savedOrderEntity);
        
        /* ================================= DELIVERY ================================ */
        deliveryRepository.save(DeliveryEntity.builder()
            .orderId(orderDto.getOrderId())
            .address(memberEntity.getAddress())
            .detailAddress(memberEntity.getDetailAddress())
            .build());

        OrderCollectionDto orderCollectionDto = new OrderCollectionDto(orderDto, orderProductDtoList);

        /* ================================ KAFKA MESSAGE ================================ */
        notificationService.sendMessage(orderCollectionDto);

        return orderCollectionDto;
        
    }


    private List<OrderProductEntity> createOrderProduct(long orderId, List<OrderProductDto> orderProductDtos){
        
        List<OrderProductEntity> orderProductEntityList = new ArrayList<>();
        for(OrderProductDto orderProductDto: orderProductDtos){
            ProductEntity productEntity = productRepository.findById(orderProductDto.getProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

            OrderProductEntity orderProductEntity = OrderProductEntity.builder()
                    .orderId(orderId)
                    .productId(orderProductDto.getProductId())
                    .productName(productEntity.getProductName())
                    .orderQuantity(orderProductDto.getOrderQuantity())
                    .orderPrice(orderProductDto.getOrderPrice())
                    .build();
            
            orderProductEntityList.add(orderProductEntity);    
        }
        return orderProductEntityList;
    }
}
