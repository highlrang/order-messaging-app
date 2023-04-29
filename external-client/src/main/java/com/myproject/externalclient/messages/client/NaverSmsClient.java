package com.myproject.externalclient.messages.client;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.myproject.externalclient.messages.config.HeaderConfiguration;
import com.myproject.externalclient.messages.dto.SmsRequestDto;
import com.myproject.externalclient.messages.dto.SmsResponseDto;

import feign.Headers;
import feign.Param;

@FeignClient(name = "naverSms", url = "${naverSms.domain}" + "${naverSms.path}", configuration = HeaderConfiguration.class)
public interface NaverSmsClient {
    
    @PostMapping("/messages")
    ResponseEntity<SmsResponseDto> send(@RequestBody SmsRequestDto smsRequestDto);
}
