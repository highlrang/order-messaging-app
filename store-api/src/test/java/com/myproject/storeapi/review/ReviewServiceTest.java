package com.myproject.storeapi.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.review.repository.ReviewRepository;
import com.myproject.core.user.domain.StoreEntity;
import com.myproject.core.user.dto.StoreDto;
import com.myproject.core.user.dto.StoreReviewDto;
import com.myproject.core.user.repository.StoreRepository;

@ExtendWith(SpringExtension.class)
public class ReviewServiceTest {
    
    @Mock StoreRepository storeRepository;
    @Mock ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 가게 평점 업데이트 TDD")
    public void manageReview(){
        /* ============== GIVEN ================== */
        long storeNo = 1l;
        double storeRating = 4.5;
        
        StoreReviewDto storeReviewDto = StoreReviewDto.of(storeNo, storeRating);
        StoreEntity givenStore = StoreEntity.builder().build();
        when(storeRepository.findByStoreNo(anyLong()))
            .thenReturn(Optional.ofNullable(givenStore));

        /* ============== WHEN ================== */
        ReviewService reviewService = new ReviewService();
        StoreDto storeDto = reviewService.manageStoreRating(storeReviewDto);

        /* ============== THEN ================== */
        assertEquals(storeDto.getStoreRating(), storeRating);
        
    }

    class ReviewService {

        StoreDto manageStoreRating(StoreReviewDto storeReviewDto){
            StoreEntity storeEntity = storeRepository.findByStoreNo(storeReviewDto.getStoreNo())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
            storeEntity.updateStoreRating(storeReviewDto.getStoreRating());
            return new StoreDto(storeEntity);
        }
        
    }
}
