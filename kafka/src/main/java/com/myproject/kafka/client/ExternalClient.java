package com.myproject.kafka.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.myproject.core.order.dto.NotificationDto;
import com.myproject.kafka.order.dto.SmsRequestDto;

@FeignClient(name = "external-client", url="${externalClient.uri}")
public interface ExternalClient {
    
    @PostMapping(value = "/messages", produces = "application/json; charset=UTF-8")
    void sendMessage(@RequestHeader Map<String,String> header, @RequestBody SmsRequestDto smsRequestDto);

}
