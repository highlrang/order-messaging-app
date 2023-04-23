package com.myproject.kafka.order.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Service;

import com.myproject.core.order.dto.NotificationDto;
import com.myproject.kafka.client.ExternalClient;

import static com.myproject.kafka.order.enums.OrderTopic.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    
    private final ExternalClient externalClient;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    // @KafkaListener(topics = [OrderTopic.ORDER_COMPLETE.getTopicName()], groupId = groupId) 
    public void orderComplete(NotificationDto notificationDto){
        try{
            

        }catch(Exception e){

        }
    }

// auto.create.topcis.enable
// --bootstrap-server --topic
}
