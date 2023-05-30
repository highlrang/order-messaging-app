package com.myproject.orderapi.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.order.domain.OrderProductEntity;
import com.myproject.core.order.repository.OrderProductRepository;
import com.myproject.core.order.repository.OrderRepository;
import com.myproject.core.review.domain.ReviewEntity;
import com.myproject.core.review.repository.ReviewRepository;
import com.myproject.orderapi.review.dto.ReviewRequestDto;
import com.myproject.orderapi.review.dto.ReviewResponseDto;

import lombok.Getter;

@ExtendWith(SpringExtension.class)
public class ReviewServiceTest {
    
    @Mock ReviewRepository reviewRepository;
    @Mock OrderProductRepository orderProductRepository;
    @InjectMocks ReviewService reviewService;

    @Test
    @DisplayName("리뷰 생성 TDD")
    public void createReview(){

        /* ========================= GIVEN =========================== */
        long orderId = 1l;
        ReviewRequestDto givenReviewDto = new ReviewRequestDto();
        ReviewEntity givenReviewEntity = ReviewEntity.builder().orderId(orderId).build();
        OrderProductEntity givenOrderProduct = OrderProductEntity.builder().orderId(orderId).build();
        when(reviewRepository.save(any(ReviewEntity.class)))
            .thenReturn(givenReviewEntity);
        when(orderProductRepository.findById(anyLong()))
            .thenReturn(Optional.ofNullable(givenOrderProduct));

        /* ========================= WHEN =========================== */
        ReviewResponseDto reviewResponseDto = reviewService.createReview(givenReviewDto);

        /* ========================= THEN =========================== */
        assertEquals(reviewResponseDto.getOrderId(), orderId);
    }



    class ReviewService {

        ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto){
            
            OrderProductEntity orderProductEntity = orderProductRepository.findById(reviewRequestDto.getOrderProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
            ReviewEntity reviewEntity = ReviewEntity.builder()
                                                        .orderId(orderProductEntity.getOrderId())
                                                        .reviewPoint(reviewRequestDto.getReviewPoint())
                                                        .reviewContent(reviewRequestDto.getReviewContent())
                                                        .build();
            ReviewEntity savedReview = reviewRepository.save(reviewEntity);
            return new ReviewResponseDto(savedReview);
        }
    }

}
