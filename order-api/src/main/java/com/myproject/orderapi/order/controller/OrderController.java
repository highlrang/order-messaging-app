package com.myproject.orderapi.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.myproject.core.common.dto.ApiResponse;
import com.myproject.core.order.dto.OrderCollectionDto;
import com.myproject.core.order.dto.OrderProductDto;
import com.myproject.core.user.dto.UserResponseDto;
import com.myproject.orderapi.order.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createOrder(
            @AuthenticationPrincipal UserResponseDto userDto,
            @RequestBody List<OrderProductDto> orderProductDtos){
        
        log.info("==== userDto is {} ====", userDto.toString());
        OrderCollectionDto orderCollectionDto = orderService.createOrder(userDto.getUserId(), orderProductDtos);
        return ResponseEntity.ok(ApiResponse.success(orderCollectionDto));
    }
}
