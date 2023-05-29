package com.myproject.orderapi.review.dto;

import com.myproject.core.review.domain.ReviewEntity;

import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private long orderId;
    private long orderProductId;
    private double reviewPoint;
    private String reviewContent;

    public ReviewResponseDto(ReviewEntity e){
        this.orderId = e.getOrderId();
        this.reviewPoint = e.getReviewPoint();
        this.reviewContent = e.getReviewContent();
    }
}
