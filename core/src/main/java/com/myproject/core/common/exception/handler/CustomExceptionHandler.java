package com.myproject.core.common.exception.handler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ApiResponse<?>> validationException(Exception e){
        
        log.error(e.toString() + "\n" + Arrays.toString(e.getStackTrace()));
        
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(ErrorCode.VALIDATION_EXCEPTION));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> customExceptionHandler(CustomException e){

        log.error(e.toString());

        return ResponseEntity.badRequest()
            .body(ApiResponse.fail(e.getErrorCode()));            
    }

    @ExceptionHandler(value = { NullPointerException.class, SQLException.class })
    protected ResponseEntity<Object> NullException(Exception e) {
        
        log.error(e.toString() + "\n" + Arrays.toString(e.getStackTrace()));
            
        return ResponseEntity.internalServerError()
            .body(ApiResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
