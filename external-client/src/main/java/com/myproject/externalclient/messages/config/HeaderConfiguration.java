package com.myproject.externalclient.messages.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class HeaderConfiguration {
    
    @Value("${naverSms.accessKey}")
    private String naverSmsAccess;

    @Value("${naverSms.secretKey}")
    private String naverSmsSecret;

    // 공통 헤더이기 때문에 @Headers나 @RequestHeader로 설정하는 대신 RequestInterceptor 사용
    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> requestTemplate
            .header("Content-Type", "application/json; charset=utf-8")
            .header("x-ncp-apigw-timestamp", String.valueOf(System.currentTimeMillis()))
            .header("x-ncp-iam-access-key", naverSmsAccess)
            .header("x-ncp-apigw-signature-v2", naverSmsSecret);
    }
}
