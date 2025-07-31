package com.quangnv.msb.core.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@UtilityClass
public class JsonFileReader {

    public static final ObjectMapper MAPPER = JsonMapper.builder()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();

    public static <T> List<T> readJsonList(String fileName, Class<T> clazz) {
        try (InputStream inputStream = new ClassPathResource(fileName).getInputStream()) {
            return MAPPER.readValue(inputStream, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }
}
