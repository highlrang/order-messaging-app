package com.myproject.kafka.order.controller;

import static com.myproject.kafka.order.constants.OrderTopic.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.core.order.dto.NotificationDto;
import com.myproject.kafka.order.producer.NotificationProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/kafka/order")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationProducer notificationProducer;

    @PostMapping("/notification")
    public void notification(@RequestBody NotificationDto notificationDto){
        notificationProducer.send(notificationDto);
    }

}
