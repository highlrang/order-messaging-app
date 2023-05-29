package com.myproject.kafka.order.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.myproject.core.order.constants.OrderTopic;
import com.myproject.core.order.dto.NotificationDto;
import com.myproject.core.order.dto.ReviewNotificationDto;
import com.myproject.core.user.dto.StoreReviewDto;
import com.myproject.kafka.client.ExternalClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SyncConsumer {

    private final ExternalClient externalClient;

    @KafkaListener(topics = OrderTopic.ORDER_REVIEW, groupId = "order-review")
    public void orderReview(ReviewNotificationDto dto){ 

        StoreReviewDto storeReviewDto = StoreReviewDto.of(dto.getStoreNo(), dto.getReviewPoint());
        externalClient.manageReview(storeReviewDto); 
        
    }
}
