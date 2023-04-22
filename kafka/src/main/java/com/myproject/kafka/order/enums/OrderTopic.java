package com.myproject.kafka.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderTopic {
    
    ORDER_COMPLETE("ORDER_COMPLETE", "orderComplete"),
    ORDER_ACCEPT("ORDER_ACCEPT", "orderAccept"),
    
    DELIVERY_START("DELIVERY_START", "deliveryStart"),
    DELIVERY_COMPLETE("DELIVERY_COMPLETE", "deliveryComplete");

    private final String code;
    private final String topicName;
}
