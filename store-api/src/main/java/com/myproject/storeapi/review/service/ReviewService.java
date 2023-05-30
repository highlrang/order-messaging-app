package com.myproject.storeapi.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.user.domain.StoreEntity;
import com.myproject.core.user.dto.StoreDto;
import com.myproject.core.user.dto.StoreReviewDto;
import com.myproject.core.user.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final StoreRepository storeRepository;
    
    @Transactional
    public StoreDto manageStoreRating(StoreReviewDto storeReviewDto){
        StoreEntity storeEntity = storeRepository.findByStoreNo(storeReviewDto.getStoreNo())
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        storeEntity.updateStoreRating(storeReviewDto.getStoreRating());
        return new StoreDto(storeEntity);
    }
}
