package com.myproject.core.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDER_COMPLETE("ORDER_COMPLETE", 110, "주문완료"),
    ORDER_ACCEPT("ORDER_ACCEPT", 120, "주문접수"),
    ORDER_FAIL("ORDER_FAIL", 130, "주문실패"),

    ORDER_CANCEL("ORDER_CANCEL", 140, "주문취소"),

    DELIVERY_START("DELIVERY_START", 200, "배달시작"),
    DELIVERY_COMPLETE("DELIVERY_COMPLETE", 210, "배달완료"),;

    private final String code;
    private final int status;
    private final String displayName;
}
