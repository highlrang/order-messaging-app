package com.myproject.orderapi.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.domain.OrderProductEntity;
import com.myproject.core.order.dto.OrderCollectionDto;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.dto.OrderProductDto;
import com.myproject.core.order.repository.OrderProductRepository;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.product.domain.ProductEntity;
import com.myproject.core.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public OrderCollectionDto createOrder(long memberId, List<OrderProductDto> orderProductDtos){

        /* ================================= ORDER ==================================== */
        OrderEntity orderEntity = new OrderEntity(memberId);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        long orderId = savedOrderEntity.getOrderId();

        /* ============================== ORDER_PRODUCT ================================ */
        List<OrderProductEntity> orderProductEntites = createOrderProduct(orderId, orderProductDtos);

        String orderName = orderProductEntites.size() > 1 ?
                            orderProductEntites.get(0).getProductName() + " 외 " + orderProductEntites.size() + "건" :
                            orderProductEntites.get(0).getProductName();
        int orderPrice = orderProductEntites.stream()
            .mapToInt(ope -> ope.getOrderPrice())
            .sum();
        List<OrderProductEntity> savedOrderProductEntities = orderProductRepository.saveAll(orderProductEntites);
        
         /* ================================= ORDER ==================================== */
        savedOrderEntity.setOrderInfo(orderName, orderPrice);
        

        return new OrderCollectionDto(
                                        new OrderDto(savedOrderEntity),
                                        savedOrderProductEntities.stream()
                                            .map(OrderProductDto::new)
                                            .collect(Collectors.toList())
                                    );
        
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
