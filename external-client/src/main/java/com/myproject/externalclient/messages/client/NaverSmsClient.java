package com.myproject.externalclient.messages.client;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.myproject.externalclient.messages.dto.SmsRequestDto;

@FeignClient(name = "naverSms", url = "${naverSms.uri}")
public interface NaverSmsClient {
    
    @PostMapping("/${serviceId}/messages")
    void send(@PathVariable(value = "serviceId") String serviceId, @RequestBody SmsRequestDto smsRequestDto);
}
