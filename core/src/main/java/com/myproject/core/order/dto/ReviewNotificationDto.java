package com.myproject.core.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewNotificationDto extends NotificationDto{
    
    private long storeNo;
    private double reviewRating;

    public ReviewNotificationDto(long orderId, String orderName, List<String> phoneNumbers, long storeNo, double reviewRating){
        super(orderId, orderName, phoneNumbers);
        this.storeNo = storeNo;
        this.reviewRating = reviewRating;
    }
}
