package com.myproject.core.user.dto;

import com.myproject.core.user.domain.StoreEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class StoreDto extends UserResponseDto {
    
    private long storeNo;
    private String storeName;
    private String storeBranch;
    private double storeRating;
    
    public StoreDto(StoreEntity e) {
        super(e);
        this.storeNo = e.getStoreNo();
        this.storeName = e.getStoreName();
        this.storeBranch = e.getStoreBranch();
        this.storeRating = e.getStoreRating();
    }
}
