package com.myproject.core.order.dto;

import java.util.List;

import com.myproject.core.order.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private OrderStatus orderStatus;
    private long orderId;
    private long deliveryId;
    private String orderName;
    private List<String> phoneNumbers;

    public NotificationDto(long orderId, String orderName, List<String> phoneNumbers){
        this.orderId = orderId;
        this.orderName = orderName;
        this.phoneNumbers = phoneNumbers;
    }
}