package com.myproject.core.order.dto;

import com.myproject.core.order.domain.OrderEntity;

import lombok.Getter;

@Getter
public class OrderDto {
    
    private long orderId;
    private long memberId;
    private String orderName;
    private int orderPrice;
    private int orderStatus;
    
    public OrderDto(OrderEntity e){
        this.orderId = e.getOrderId();
        this.memberId = e.getMemberId();
        this.orderName = e.getOrderName();
        this.orderPrice = e.getOrderPrice();
        this.orderStatus = e.getOrderStatus();
    }
}
