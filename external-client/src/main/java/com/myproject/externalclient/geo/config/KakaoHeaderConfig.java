package com.myproject.externalclient.geo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import feign.RequestInterceptor;

@Configuration
public class KakaoHeaderConfig {
    
    @Value("${kakao.restApiKey}")
    private String kakaoRestKey;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean(name = "kakaoInterceptor")
    public RequestInterceptor requestInterceptor() {
        return template -> template.header(AUTHORIZATION_HEADER, "KakaoAK " + kakaoRestKey);
    }
}
