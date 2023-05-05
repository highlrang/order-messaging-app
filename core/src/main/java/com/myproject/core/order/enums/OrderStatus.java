package com.myproject.core.order.enums;

import java.util.Arrays;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDER_PROCESSING("ORDER_PROCESSING", 100, "주문중"),
    ORDER_COMPLETE("ORDER_COMPLETE", 110, "주문완료"),
    ORDER_FAIL("ORDER_FAIL", 120, "주문실패"),

    ORDER_ACCEPT("ORDER_ACCEPT", 200, "주문접수"),
    ORDER_REJECT("ORDER_REJECT", 210, "주문 거절"),

    ORDER_CANCEL("ORDER_CANCEL", 230, "주문취소"),

    DELIVERY_START("DELIVERY_START", 300, "배달시작"),
    DELIVERY_COMPLETE("DELIVERY_COMPLETE", 310, "배달완료"),;

    private final String code;
    private final int status;
    private final String displayName;

    public static OrderStatus of(int status) throws Exception {
        return Arrays.asList(OrderStatus.values())
            .stream()
            .filter(os -> os.getStatus() == status)
            .findAny()
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ORDER_STATUS));
            
            
            
            
    }
}
