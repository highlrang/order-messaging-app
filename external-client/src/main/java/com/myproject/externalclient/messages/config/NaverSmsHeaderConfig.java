package com.myproject.externalclient.messages.config;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request.HttpMethod;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class NaverSmsHeaderConfig {
    
    @Value("${naverSms.domain}")
    private String naverSmsDomain;

    @Value("${naverSms.accessKey}")
    private String naverSmsAccess;

    @Value("${naverSms.secretKey}")
    private String naverSmsSecret;

    // 공통 헤더이기 때문에 @Headers나 @RequestHeader로 설정하는 대신 RequestInterceptor 사용
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            Long timeStamp = System.currentTimeMillis();
            String timeStampStr = timeStamp.toString();

            String url = template.feignTarget().url() + template.request().url();
            String path = url.replace(naverSmsDomain, "");
            String signature = makeSignature(timeStampStr, template.method(), path);

            template
                .header("accept", "application/json")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("x-ncp-apigw-timestamp", timeStampStr)
                .header("x-ncp-iam-access-key", naverSmsAccess)
                .header("x-ncp-apigw-signature-v2", signature);
        };

    }

    private String makeSignature(String timeStamp, String method, String path) {
        
        String space = " ";
        String newLine = "\n";

        String message = new StringBuilder()
            .append(method)
            .append(space)
            .append(path)
            .append(newLine)
            .append(timeStamp)
            .append(newLine)
            .append(naverSmsAccess)
            .toString();
        log.info("====message is {}====", message);
    
        String encodeBase64String;
        try{
            SecretKeySpec signingKey = new SecretKeySpec(naverSmsSecret.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
        
            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            encodeBase64String = Base64.encodeBase64String(rawHmac);

        }catch(Exception e){
            log.error("[NaverSms Feign Signature 생성 실패] error = {} \n {}", e.toString(), Arrays.toString(e.getStackTrace()));
            encodeBase64String = "";
        }
    
        
        return encodeBase64String;
    }
    
}
