package com.myproject.externalclient.messages.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.externalclient.messages.client.NaverSmsClient;
import com.myproject.externalclient.messages.dto.SmsRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final NaverSmsClient naverSmsClient;

    @Value("${naverSms.serviceId}")
    private String serviceId;

    @PostMapping
    public void send(@RequestBody SmsRequestDto smsRequestDto){
        log.info(smsRequestDto.toString());
        naverSmsClient.send(serviceId, smsRequestDto);
    }

}
