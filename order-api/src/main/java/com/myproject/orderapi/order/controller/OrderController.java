package com.myproject.orderapi.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponse<?>> createOrder(@RequestBody List<OrderProductDto> orderProductDtos){
        
        // TODO security
        long memberId = 1l;
        OrderCollectionDto orderCollectionDto = orderService.createOrder(memberId, orderProductDtos);
        return ResponseEntity.ok(ApiResponse.success(orderCollectionDto));
    }
}
