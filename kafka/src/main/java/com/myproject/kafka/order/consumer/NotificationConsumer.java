package com.myproject.kafka.order.consumer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.myproject.core.order.dto.NotificationDto;
import com.myproject.core.utils.EncryptionUtil;
import com.myproject.kafka.client.ExternalClient;
import com.myproject.kafka.order.constants.OrderTopic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    
    private final ExternalClient externalClient;

    @Value("${clientServer.secretKey}")
    private String clientHeaderKey;

    @KafkaListener(topics = OrderTopic.ORDER_COMPLETE, groupId = "${spring.kafka.consumer.group-id}") // containerFactory = "kafkaListenerContainerFactory")
    public void orderComplete(@Payload NotificationDto notificationDto){
        try{
            log.info("====consumer====");

            Map<String, String> header = Map.of("Authorization", EncryptionUtil.encrypt(clientHeaderKey));
            // externalClient.notification(header, notificationDto);

        }catch(Exception e){

        }
    }

// auto.create.topcis.enable
// --bootstrap-server --topic
}
