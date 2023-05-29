package com.myproject.orderapi.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.review.domain.ReviewEntity;
import com.myproject.core.review.repository.ReviewRepository;
import com.myproject.orderapi.review.dto.ReviewRequestDto;
import com.myproject.orderapi.review.dto.ReviewResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto){
        
        ReviewEntity reviewEntity = ReviewEntity.builder()
                                                    .orderId(reviewRequestDto.getOrderId())
                                                    .reviewPoint(reviewRequestDto.getReviewPoint())
                                                    .reviewContent(reviewRequestDto.getReviewContent())
                                                    .build();
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        return new ReviewResponseDto(savedReview);
    
    } 
}
