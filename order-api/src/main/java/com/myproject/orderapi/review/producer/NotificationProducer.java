package com.myproject.orderapi.review.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.myproject.core.order.constants.OrderTopic;
import com.myproject.core.order.dto.ReviewNotificationDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationProducer {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(ReviewNotificationDto dto){
        kafkaTemplate.send(OrderTopic.ORDER_REVIEW, dto);
    }
}
