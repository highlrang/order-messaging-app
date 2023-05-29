package com.myproject.orderapi.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    
    private long orderProductId;
    private double reviewPoint;
    private String reviewContent;
}
