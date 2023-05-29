package com.myproject.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreReviewDto {
    
    private long storeNo;
    private double storeRating;

    public static StoreReviewDto of(long storeNo, double storeRating){
        StoreReviewDto dto = new StoreReviewDto(storeNo, storeRating);
        return dto;
    }
}
