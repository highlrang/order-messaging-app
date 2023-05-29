package com.myproject.externalclient.store.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.externalclient.store.client.StoreClient;
import com.myproject.externalclient.store.dto.StoreReviewDto;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
@RestController
public class StoreController {
    
    private final StoreClient storeClient;
    
    @PostMapping("/review")
    public void manageReview(@RequestBody StoreReviewDto storeReviewDto){
        storeClient.manageReview(storeReviewDto);
    }
}
