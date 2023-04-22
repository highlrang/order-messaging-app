package com.myproject.kafka.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.kafka.order.producer.NotificationProducer;
import static com.myproject.kafka.order.enums.OrderTopic.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/kafka/order")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationProducer notificationProducer;

    @GetMapping("/orderComplete/{orderId}")
    public void orderComplete(@PathVariable("orderId") long orderId){
        notificationProducer.sendId(ORDER_COMPLETE.getTopicName(), orderId);
    }

    @GetMapping("/orderAccept/{orderId}")
    public void orderAccept(@PathVariable("orderId") long orderId){
        notificationProducer.sendId(ORDER_ACCEPT.getTopicName(), orderId);
    }

    @GetMapping("/deliveryStart/{deliveryId}")
    public void deliveryStart(@PathVariable("deliveryId") long deliveryId){
        notificationProducer.sendId(DELIVERY_START.getTopicName(), deliveryId);
    }

    @GetMapping("/deliveryComplete/{deliveryId}")
    public void deliveryComplete(@PathVariable("deliveryId") long deliveryId){
        notificationProducer.sendId(DELIVERY_COMPLETE.getTopicName(), deliveryId);
    }

}
