package com.myproject.externalclient.messages.client;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.myproject.externalclient.messages.config.HeaderConfiguration;
import com.myproject.externalclient.messages.dto.SmsRequestDto;
import com.myproject.externalclient.messages.dto.SmsResponseDto;

import feign.Headers;
import feign.Param;

@FeignClient(name = "naverSms", url = "${naverSms.uri}", configuration = HeaderConfiguration.class)
public interface NaverSmsClient {
    
    @PostMapping("/${serviceId}/messages")
    SmsResponseDto send(
            @PathVariable(value = "serviceId") String serviceId,
            @RequestBody SmsRequestDto smsRequestDto
    );
}
