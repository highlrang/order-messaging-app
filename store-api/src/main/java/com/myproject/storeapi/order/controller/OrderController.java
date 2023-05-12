package com.myproject.storeapi.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.core.common.dto.ApiResponse;
import com.myproject.core.user.dto.UserResponseDto;
import com.myproject.storeapi.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    
    private final OrderService orderService;

    @GetMapping("/{orderId}/accept")
    public ResponseEntity<ApiResponse<?>> acceptOrder(
                                                        @AuthenticationPrincipal UserResponseDto userDto,
                                                        @PathVariable("orderId") long orderId
    ){
        System.out.println("userDto = " + userDto);
        return ResponseEntity.ok(ApiResponse.success(orderService.acceptOrder(userDto.getUserId(), orderId)));
    }

    @GetMapping("/{orderId}/reject")
    public ResponseEntity<ApiResponse<?>> rejectOrder(
                                                        @AuthenticationPrincipal UserResponseDto userDto,
                                                        @PathVariable("orderId") long orderId
    ){
        return ResponseEntity.ok(ApiResponse.success(orderService.rejectOrder(userDto.getUserId(), orderId)));
    }
    
}
