package com.myproject.externalclient.messages.dto;

import lombok.*;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class SmsRequestDto {
    
    private String type; // SMS, LMS, MMS
    private String from; // 사전 등록된 발신 번호
    private String content; // 기본 내용
    private List<MessageDto> messages; // 최대 100개

    @ToString
    @Getter
    @NoArgsConstructor
    static class MessageDto{
        private String to; // 수신번호
        private String content; // 내용
    }

}