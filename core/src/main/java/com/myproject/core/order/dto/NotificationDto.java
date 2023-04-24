package com.myproject.core.order.dto;

import com.myproject.core.order.enums.OrderStatus;

import lombok.Getter;

@Getter
public class NotificationDto {

    private OrderStatus orderStatus;
    private long objectId;
    private String phoneNumber;

}
