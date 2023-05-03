package com.myproject.core.order.dto;

import com.myproject.core.order.domain.OrderProductEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto {
    private long productId;
    private int orderPrice;
    private int orderQuantity;

    public OrderProductDto(OrderProductEntity e){
        this.productId = e.getProductId();
        this.orderPrice = e.getOrderPrice();
        this.orderQuantity = e.getOrderQuantity();
    }
}
