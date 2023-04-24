package com.myproject.core.common.exception;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.enums.StatusCode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
