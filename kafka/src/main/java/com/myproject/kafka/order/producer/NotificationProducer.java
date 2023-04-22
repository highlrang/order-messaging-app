package com.myproject.kafka.order.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendId(String topic, long id){
        kafkaTemplate.send(topic, id);
    }

    
}

