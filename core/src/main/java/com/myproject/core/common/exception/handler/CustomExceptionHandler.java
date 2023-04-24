package com.myproject.core.common.exception.handler;

import java.sql.SQLException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myproject.core.common.dto.ApiResponse;
import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> customExceptionHandler(CustomException e){

        log.error(e.toString());

        return ResponseEntity.badRequest()
            .body(ApiResponse.fail(e.getErrorCode()));            
    }

    @ExceptionHandler(value = { NullPointerException.class, SQLException.class })
    protected ResponseEntity<Object> NullException(NullPointerException npe, SQLException se) {
        
        if(npe != null)
            log.error(npe.toString() + "\n" + Arrays.toString(npe.getStackTrace()));

        if(se != null)
            log.error(se.toString() + "\n" + Arrays.toString(se.getStackTrace()));
            
        return ResponseEntity.internalServerError()
            .body(ApiResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
