package com.myproject.core.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
    
    SUCCESS("SUCCESS", 1000),
    
    ERROR("ERROR", 2000),
    ;

    private final String code;
    private final int status;
}
