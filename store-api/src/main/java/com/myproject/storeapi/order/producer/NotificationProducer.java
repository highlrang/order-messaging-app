package com.myproject.storeapi.order.producer;

import java.lang.reflect.Field;
import java.util.Arrays;

// import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.myproject.core.order.dto.NotificationDto;
import com.myproject.core.order.enums.OrderStatus;
import com.myproject.core.order.constants.OrderTopic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducer {

    // private final KafkaTemplate<String, Object> kafkaTemplate;

    private String getTopicName(String code){
        String topicName = null;

        try{
            Class<?> orderTopicClass = OrderTopic.class;
            Field field = orderTopicClass.getField(code);
            field.setAccessible(true);
            topicName = field.get(null).toString();

        }catch (Exception e){
            log.error("TOPIC NAME CREATE ERR {} \n{}", e.toString(), Arrays.toString(e.getStackTrace()));
            
        }
        
        return topicName;
        
    }

    public void send(NotificationDto notificationDto){
        log.info("====producer====");

        OrderStatus orderStatus = notificationDto.getOrderStatus();
        String topicName = getTopicName(orderStatus.getCode());
        // if(topicName != null)
            // kafkaTemplate.send(topicName, notificationDto);
    }

    
}

