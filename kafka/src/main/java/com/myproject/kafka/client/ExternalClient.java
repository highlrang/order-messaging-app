package com.myproject.kafka.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.myproject.core.order.dto.NotificationDto;

@FeignClient(name = "external-client", url="${externalClient.url}")
public interface ExternalClient {
    
    @PostMapping(value = "/notification", headers = "")
    void notification(@RequestBody NotificationDto notificationDto);

}
