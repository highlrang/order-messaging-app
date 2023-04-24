package com.myproject.core.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDER_COMPLETE("ORDER_COMPLETE", 110),
    ORDER_ACCEPT("ORDER_ACCEPT", 120),
    ORDER_FAIL("ORDER_FAIL", 130),

    ORDER_CANCEL("ORDER_CANCEL", 140),

    DELIVERY_START("DELIVERY_START", 200),
    DELIVERY_COMPLETE("DELIVERY_COMPLETE", 210),;

    private final String code;
    private final int no;
}
