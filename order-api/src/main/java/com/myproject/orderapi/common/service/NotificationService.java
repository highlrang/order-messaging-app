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
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.domain.StoreEntity;
import com.myproject.core.user.repository.MemberRepository;
import com.myproject.core.user.repository.StoreRepository;
import com.myproject.orderapi.order.producer.NotificationProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final NotificationProducer notificationProducer;

    public void sendMessage(OrderCollectionDto orderCollectionDto){

        try{
            OrderDto orderDto = orderCollectionDto.getOrderDto();
            List<OrderProductDto> orderProductDtos = orderCollectionDto.getOrderProductDtos();

            MemberEntity memberEntity = memberRepository.findById(orderDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
            List<String> phoneNumbers = new ArrayList<>();
            phoneNumbers.add(memberEntity.getPhoneNumber());

            OrderStatus orderStatus = OrderStatus.of(orderDto.getOrderStatus());  

            List<Long> productIds = orderProductDtos.stream().map(OrderProductDto::getProductId).collect(Collectors.toList());
            List<StoreEntity> storeEntities = storeRepository.findAllByProductIds(productIds);
            phoneNumbers.addAll(storeEntities.stream().map(s -> s.getPhoneNumber()).distinct().collect(Collectors.toList()));

            
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
}
