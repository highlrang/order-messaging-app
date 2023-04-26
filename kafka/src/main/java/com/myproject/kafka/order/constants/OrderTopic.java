package com.myproject.kafka.order.constants;

import lombok.Getter;

@Getter
public class OrderTopic {
    
    public static final String ORDER_COMPLETE = "order-complete";
    public static final String ORDER_ACCEPT = "order-accept";
    public static final String DELIVERY_START = "delivery-start";
    public static final String DELIVERY_COMPLETE = "delivery-complete";

}
