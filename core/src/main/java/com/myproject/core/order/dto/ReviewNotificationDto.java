package com.myproject.core.order.dto;

import lombok.Getter;

@Getter
public class ReviewNotificationDto extends NotificationDto{
    
    private long storeNo;
    private int reviewPoint;
}
