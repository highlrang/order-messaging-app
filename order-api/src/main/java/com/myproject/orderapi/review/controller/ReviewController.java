package com.myproject.orderapi.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.core.common.dto.ApiResponse;
import com.myproject.orderapi.review.dto.ReviewRequestDto;
import com.myproject.orderapi.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {
    
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createReview(@RequestBody ReviewRequestDto reviewDto) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.createReview(reviewDto)));
    }
}
