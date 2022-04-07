package com.aadhikat.rsocket.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;

public class ObjectUtil {

    public static Payload toPayload(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return DefaultPayload.create(objectMapper.writeValueAsBytes(o));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(Payload payload, Class<T> type) {
        try {
            return new ObjectMapper().readValue(payload.getData().array(), type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
