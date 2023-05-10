package com.myproject.storeapi.delivery.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.core.common.dto.ApiResponse;
import com.myproject.storeapi.delivery.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController {
    
    private final DeliveryService deliveryService;

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<?>> startDelivery(@PathVariable("orderId") long orderId){
        return ResponseEntity.ok(ApiResponse.success(deliveryService.startDelivery(orderId)));
    }
}
