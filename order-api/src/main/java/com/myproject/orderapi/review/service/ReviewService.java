package com.myproject.orderapi.review.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.domain.OrderEntity;
import com.myproject.core.order.domain.OrderProductEntity;
import com.myproject.core.order.dto.OrderCollectionDto;
import com.myproject.core.order.dto.OrderDto;
import com.myproject.core.order.dto.OrderProductDto;
import com.myproject.core.order.dto.ReviewNotificationDto;
import com.myproject.core.order.repository.OrderProductRepository;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.review.domain.ReviewEntity;
import com.myproject.core.review.repository.ReviewRepository;
import com.myproject.core.user.repository.StoreRepository;
import com.myproject.orderapi.common.service.NotificationService;
import com.myproject.orderapi.review.dto.ReviewRequestDto;
import com.myproject.orderapi.review.dto.ReviewResponseDto;
import com.myproject.orderapi.review.producer.NotificationProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final OrderProductRepository orderProductRepository;
    private final NotificationService notificationService;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto){
        
        // 리뷰 등록
        OrderProductEntity orderProductEntity = orderProductRepository.findById(reviewRequestDto.getOrderProductId())
                                    .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        ReviewEntity reviewEntity = ReviewEntity.builder()
                                                    .orderId(orderProductEntity.getOrderId())
                                                    .orderProductId(reviewRequestDto.getOrderProductId())
                                                    .reviewPoint(reviewRequestDto.getReviewPoint())
                                                    .reviewContent(reviewRequestDto.getReviewContent())
                                                    .build();
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        ReviewResponseDto reviewDto = new ReviewResponseDto(savedReview);

        // 리뷰 등록 메세지 발행
        notificationService.sendReviewMessage(reviewDto);

        return reviewDto;
    
    } 
}
