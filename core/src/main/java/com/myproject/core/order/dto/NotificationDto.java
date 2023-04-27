package com.myproject.core.order.dto;

import com.myproject.core.order.enums.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor // ?
public class NotificationDto {

    private OrderStatus orderStatus;
    private long objectId;
    private String orderName;
    private String phoneNumber;

}