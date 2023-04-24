package com.myproject.core.common.dto;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.enums.StatusCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private int status;
    private String code;
    private String message;
    private T data;
    
    public static ApiResponse<?> success(Object data){
        return ApiResponse.builder()
            .status(StatusCode.SUCCESS.getStatus())
            .code(StatusCode.SUCCESS.getCode())
            .data(data)
            .build();
    }

    public static ApiResponse<?> fail(ErrorCode errorCode){
        return ApiResponse.builder()
            .status(StatusCode.ERROR.getStatus())
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build();
    }
}
