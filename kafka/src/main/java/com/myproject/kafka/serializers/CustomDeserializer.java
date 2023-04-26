package com.myproject.kafka.serializers;

import java.util.Arrays;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor
public class CustomDeserializer implements Deserializer<Object>{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object deserialize(String topic, byte[] data) {

        Object dataObj = null;
        try{
            if(data != null){
                String dataStr = new String(data, "UTF-8");
                log.info("===== dataStr is {} ====", dataStr);

                Map<String, Object> dataMap = objectMapper.readValue(dataStr, new TypeReference<Map<String,Object>>() {});

                String className = dataMap.get("class").toString();
                Class<?> clazz = Class.forName(className);

                dataObj = objectMapper.readValue(dataMap.get("data").toString(), clazz);


            }

        }catch(Exception e){
            log.error(e.toString() + "\n" + Arrays.toString(e.getStackTrace()));
        }

        log.info("==== dataObj is {} ====", dataObj);
        return dataObj;
    }
    
}
