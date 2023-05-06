package com.myproject.externalclient.geo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class KakaoHeaderConfig {
    
    @Value("${kakao.restApiKey}")
    private String kakaoRestKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("KakaoAK", kakaoRestKey);
    }
}
