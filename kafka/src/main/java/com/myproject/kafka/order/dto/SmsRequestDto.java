package com.myproject.kafka.order.dto;

import lombok.*;
import java.util.List;

import com.myproject.core.order.dto.NotificationDto;

import java.util.Arrays;

@Getter
public class SmsRequestDto {
    
    private String type; // SMS, LMS, MMS
    private String from; // 사전 등록된 발신 번호
    private String content; // 기본 내용
    private List<MessageDto> messages; // 최대 100개

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class MessageDto{
        private String to; // 수신번호
        private String content; // 내용
    }

    public SmsRequestDto(){
        this.type = "SMS";
        this.from = "${naverSmsFrom}";
        this.content = "[ORDER-MESSASING]";
    }

    public static SmsRequestDto makeMsg(NotificationDto notificationDto){
        SmsRequestDto dto = new SmsRequestDto();
        dto.messages = Arrays.asList(createMessageDto(notificationDto));
        return dto;
    }

    private static MessageDto createMessageDto(NotificationDto notificationDto){
        String content = String.format("주문정보 {}번 {}\n {}되었습니다.", 
                            notificationDto.getObjectId(), 
                            notificationDto.getOrderName(),
                            notificationDto.getOrderStatus().getDisplayName());

        return new MessageDto(notificationDto.getPhoneNumber(), content);
    }
}
