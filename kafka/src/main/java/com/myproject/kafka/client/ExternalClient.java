package com.myproject.kafka.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.myproject.core.order.dto.NotificationDto;

@FeignClient(name = "external-client", url="${externalClient.url}")
public interface ExternalClient {
    
    @PostMapping(value = "/notification") // producer
    void notification(@RequestHeader Map<String,String> header, @RequestBody NotificationDto notificationDto);

}
