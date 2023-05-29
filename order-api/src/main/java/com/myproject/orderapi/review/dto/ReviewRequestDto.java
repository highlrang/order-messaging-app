package com.myproject.orderapi.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    
    private long orderId;
    private double reviewPoint;
    private String reviewContent;
}
