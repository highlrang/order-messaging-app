package com.myproject.core.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 오류입니다."),

    KAFKA_TOPIC_EXCEPTION("KAFKA_TOPIC_EXCEPTION", "카프카 토픽 오류입니다");

    private final String code;
    private final String message;
}
