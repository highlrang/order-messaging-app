package com.myproject.orderapi.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.review.domain.ReviewEntity;
import com.myproject.core.review.repository.ReviewRepository;
import com.myproject.orderapi.review.dto.ReviewRequestDto;
import com.myproject.orderapi.review.dto.ReviewResponseDto;

import lombok.Getter;

@ExtendWith(SpringExtension.class)
public class ReviewServiceTest {
    
    @Mock ReviewRepository reviewRepository;
    @InjectMocks ReviewService reviewService;

    @Test
    @DisplayName("리뷰 생성 TDD")
    public void createReview(){

        /* ========================= GIVEN =========================== */
        long orderId = 1l;
        ReviewRequestDto givenReviewDto = new ReviewRequestDto();
        ReviewEntity givenReviewEntity = ReviewEntity.builder().orderId(orderId).build();
        when(reviewRepository.save(any(ReviewEntity.class)))
            .thenReturn(givenReviewEntity);

        /* ========================= WHEN =========================== */
        ReviewResponseDto reviewResponseDto = reviewService.createReview(givenReviewDto);

        /* ========================= THEN =========================== */
        assertEquals(reviewResponseDto.getOrderId(), orderId);
    }



    class ReviewService {

        ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto){
            
            ReviewEntity reviewEntity = ReviewEntity.builder()
                                                        .orderId(reviewRequestDto.getOrderId())
                                                        .reviewPoint(reviewRequestDto.getReviewPoint())
                                                        .reviewContent(reviewRequestDto.getReviewContent())
                                                        .build();
            ReviewEntity savedReview = reviewRepository.save(reviewEntity);
            return new ReviewResponseDto(savedReview);
        }
    }

}
