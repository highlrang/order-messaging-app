package com.myproject.externalclient.messages.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class SmsResponseDto {
    
    private String requestId;
    private String requestTime;
    private String statusCode;
    private String statusName;
}
