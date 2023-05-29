package com.myproject.externalclient.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.myproject.externalclient.store.dto.StoreReviewDto;

@FeignClient(name = "store-client", url = "${store-api.url}")
public interface StoreClient {
    
    @PostMapping("/review")
    void manageReview(StoreReviewDto storeReviewDto);
}
