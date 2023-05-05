package com.myproject.kafka.order.consumer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.myproject.core.common.utils.EncryptionUtil;
import com.myproject.core.order.dto.NotificationDto;
import com.myproject.kafka.client.ExternalClient;
import com.myproject.core.order.constants.OrderTopic;
import com.myproject.kafka.order.dto.SmsRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    
    private final ExternalClient externalClient;

    @Value("${externalClient.naverSms.from}")
    private String naverSmsFrom;

    @KafkaListener(topics = OrderTopic.ORDER_COMPLETE, groupId = "${spring.kafka.consumer.group-id}") // containerFactory = "kafkaListenerContainerFactory")
    public void orderComplete(NotificationDto notificationDto){ // @Payload
        try{
            log.info("====consumer====");

            SmsRequestDto smsRequestDto = SmsRequestDto.makeMsg(naverSmsFrom, notificationDto);
            externalClient.sendMessage(smsRequestDto);

            log.info("smsRequestDto = " + smsRequestDto.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
