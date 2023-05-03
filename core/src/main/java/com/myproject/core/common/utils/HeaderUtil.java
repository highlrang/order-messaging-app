package com.myproject.core.common.utils;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.user.domain.UserEntity;
import com.myproject.core.user.dto.UserResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HeaderUtil {

    private static String secretKey;
    @Value("${header.secretKey}")
    public void setSecretKey(String secretKey){
        HeaderUtil.secretKey = secretKey;
    }
    private static final String AUTHENTICATION_HEADER = "Authorization";
    
    public static UserResponseDto getAuth(HttpServletRequest request) {
        
        String authHeader = request.getHeader(AUTHENTICATION_HEADER);
        UserResponseDto userDto = null;

        if(authHeader != null){
            try {
                String userStr = EncryptionUtil.decrypt(secretKey, authHeader);
                ObjectMapper objectMapper = new ObjectMapper();
                userDto = objectMapper.readValue(userStr, UserResponseDto.class);

            } catch (Exception e) {
                log.error(e.toString() + "\n" + Arrays.toString(e.getStackTrace()));
            }
        }

        return userDto;
    }
    
}
