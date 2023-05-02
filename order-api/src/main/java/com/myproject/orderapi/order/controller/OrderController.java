package com.myproject.orderapi.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.myproject.core.common.dto.ApiResponse;
import com.myproject.core.order.dto.OrderCollectionDto;
import com.myproject.core.order.dto.OrderProductDto;
import com.myproject.orderapi.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createOrder(
            @SessionAttribute("user") UserResponseDto memberDto,
            @RequestBody List<OrderProductDto> orderProductDtos){
        
        OrderCollectionDto orderCollectionDto = orderService.createOrder(memberDto.getMemberId(), orderProductDtos);
        return ResponseEntity.ok(ApiResponse.success(orderCollectionDto));
    }
}
