package com.myproject.kafka.order.dto;

import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

import com.myproject.core.order.dto.NotificationDto;

import java.util.Arrays;

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
    @AllArgsConstructor
    static class MessageDto{
        private String to; // 수신번호
        private String content; // 내용
    }

    private SmsRequestDto(String from){
        this.type = "SMS";
        this.from = from;
        this.content = "[ORDER-MESSASING]";
    }

    public static SmsRequestDto inputMsg(String from, List<String> to, String msg){
        SmsRequestDto dto = new SmsRequestDto(from);
        dto.messages = to.stream()
                                .map(phoneNumber -> new MessageDto(phoneNumber, msg))
                                .collect(Collectors.toList());
        return dto;
    }

    public static SmsRequestDto makeMsg(String from, NotificationDto notificationDto){
        SmsRequestDto dto = new SmsRequestDto(from);
        dto.messages = createMessageDto(notificationDto);
        return dto;
    }

    private static List<MessageDto> createMessageDto(NotificationDto notificationDto){
        String content = String.format("주문정보 %d번 %s %s되었습니다.", 
                            notificationDto.getOrderId(), 
                            notificationDto.getOrderName(),
                            notificationDto.getOrderStatus().getDisplayName());
        return notificationDto.getPhoneNumbers().stream()
            .map(phoneNumber -> new MessageDto(phoneNumber, content))
            .collect(Collectors.toList());
        
    }
}
