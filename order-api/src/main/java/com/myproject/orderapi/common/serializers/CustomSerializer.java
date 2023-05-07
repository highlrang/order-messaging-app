package com.myproject.orderapi.common.serializers;

import java.util.Arrays;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class CustomSerializer implements Serializer<Object>{
     
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Object data) {
        
        log.info("==== data is {} =====", data);
        log.info("==== data class is {} ====", data.getClass());

        byte[] byteData = null;
        try{
            if(data != null){
                // class명 추가
                Map<String, Object> dataMap = 
                     Map.of("data", objectMapper.writeValueAsString(data), "class", data.getClass());
                byteData = objectMapper.writeValueAsBytes(dataMap);
            }

        }catch(Exception e){
            log.error(e.toString() + "\n" + Arrays.toString(e.getStackTrace()));

        }

        log.info("==== byteData is {} =====", byteData);
        return byteData;
    }
    
}
