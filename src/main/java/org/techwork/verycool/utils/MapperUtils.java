package org.techwork.verycool.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.techwork.verycool.exceptions.TransformObjectException;

import java.io.File;
import java.io.IOException;

public class MapperUtils {
    private static final ObjectMapper objectMapper = objectMapper();

    @Bean
    public static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
        return mapper;
    }

    public static <T, S> T transformToObject(S srcObject, Class<T> classType) {
        if (srcObject == null || classType == null) {
            return null;
        }

        try {
            if (srcObject instanceof File) {
                return objectMapper.readValue((File) srcObject, classType);
            } else if (srcObject instanceof String) {
                return objectMapper.readValue((String) srcObject, classType);
            } else if (classType == String.class) {
                return classType.cast(objectMapper.writeValueAsString(srcObject));
            } else {
                return objectMapper.convertValue(srcObject, classType);
            }
        } catch (IOException e) {
            throw new TransformObjectException("Error transforming object: " + e.getMessage());
        }
    }
    public static <T> String writeValueAsString(T srcObject) {
        return transformToObject(srcObject, String.class);
    }
}
