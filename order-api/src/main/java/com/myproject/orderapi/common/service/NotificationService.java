package com.myproject.orderapi.common.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.dto.NotificationDto;
import com.myproject.core.order.dto.OrderCollectionDto;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.dto.OrderProductDto;
import com.myproject.core.order.dto.ReviewNotificationDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.repository.OrderProductRepository;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.review.domain.ReviewEntity;
import com.myproject.core.review.repository.ReviewRepository;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.domain.StoreEntity;
import com.myproject.core.user.repository.MemberRepository;
import com.myproject.core.user.repository.StoreRepository;
import com.myproject.orderapi.order.producer.NotificationProducer;
import com.myproject.orderapi.review.dto.ReviewResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final NotificationProducer notificationProducer;

    public void sendMessage(OrderCollectionDto orderCollectionDto){

        try{
            OrderDto orderDto = orderCollectionDto.getOrderDto();
            OrderStatus orderStatus = OrderStatus.of(orderDto.getOrderStatus());  

            List<String> phoneNumbers = getPhoneNumbers(orderCollectionDto);
            
            NotificationDto notificationDto = NotificationDto.builder()
                .orderStatus(orderStatus)
                .orderId(orderDto.getOrderId())
                .orderName(orderDto.getOrderName())
                .phoneNumbers(phoneNumbers)
                .build();

            notificationProducer.send(notificationDto);

        }catch(Exception e){
            log.error(e.toString() + "\n" + Arrays.asList(e.getStackTrace()));
            
        }
    }

    public void sendReviewMessage(ReviewResponseDto reviewDto){
        long orderId = reviewDto.getOrderId();
        OrderDto orderDto = new OrderDto(orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND)));
        List<OrderProductDto> orderProductDtos = orderProductRepository.findByOrderId(orderId)
                                                                        .stream()
                                                                        .map(OrderProductDto::new)
                                                                        .collect(Collectors.toList());

        OrderCollectionDto orderCollectionDto = new OrderCollectionDto(orderDto, orderProductDtos);
        

        List<String> phoneNumbers = getPhoneNumbers(orderCollectionDto);
        
        StoreEntity storeEntity = storeRepository.findByOrderProductId(reviewDto.getOrderProductId())
                                            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        List<ReviewEntity> reviewEntities = reviewRepository.findByStoreNo(storeEntity.getStoreNo());
        double sum = reviewEntities.stream()
            .map(r -> r.getReviewPoint())
            .reduce(0d, (prev, next) -> prev + next);
        int storeRating = (int) sum / reviewEntities.size();

        ReviewNotificationDto reviewNotiDto = new ReviewNotificationDto(
                                                                        orderId,
                                                                        orderDto.getOrderName(),
                                                                        phoneNumbers,
                                                                        storeEntity.getStoreNo(),
                                                                        storeRating
                                                                        );
        notificationProducer.send(reviewNotiDto);
    }

    private List<String> getPhoneNumbers(OrderCollectionDto orderCollectionDto){
        OrderDto orderDto = orderCollectionDto.getOrderDto();
        MemberEntity memberEntity = memberRepository.findById(orderDto.getMemberId())
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(memberEntity.getPhoneNumber());

        List<OrderProductDto> orderProductDtos = orderCollectionDto.getOrderProductDtos();
        List<Long> productIds = orderProductDtos.stream().map(OrderProductDto::getProductId).collect(Collectors.toList());
        List<StoreEntity> storeEntities = storeRepository.findAllByProductIds(productIds);
        phoneNumbers.addAll(storeEntities.stream().map(s -> s.getPhoneNumber()).distinct().collect(Collectors.toList()));

        return phoneNumbers;
    }
}
