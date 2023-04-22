package com.myproject.kafka.order.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Service;

import static com.myproject.kafka.order.enums.OrderTopic.*;

import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor
public class NotificationConsumer {
    
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    // private final OrderRepository orderRepository;

    // @KafkaListener(topics = [OrderTopic.ORDER_COMPLETE.getTopicName()], groupId = groupId) 
    public void orderComplete(){
        try{
            // 폰번 조회 및 메세지 날리기

        }catch(Exception e){

        }
    }

// auto.create.topcis.enable
// --bootstrap-server --topic
}
