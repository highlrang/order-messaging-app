package com.myproject.core.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", "계정 인증에 실패했습니다."),
    
    VALIDATION_EXCEPTION("VALIDATION_EXCEPTION", "데이터 유효성 검증에 실패했습니다."),
    DATA_NOT_FOUND("DATA_NOT_FOUND", "데이터를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 오류입니다."),

    KAFKA_TOPIC_EXCEPTION("KAFKA_TOPIC_EXCEPTION", "카프카 토픽 오류입니다");

    private final String code;
    private final String message;
}
