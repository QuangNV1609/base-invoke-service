package com.quangnv.msb.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.quangnv.msb.core.exception.MAErrorCode;
import com.quangnv.msb.core.exception.UnExpectedException;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class JsonHelpers {

    private static final Logger log = LoggerFactory.getLogger(JsonHelpers.class);

    public static final ObjectMapper MAPPER = JsonMapper.builder()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();

    public static final Gson GSON = new Gson();

    public static String object2Gson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T gson2Object(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static String object2Json(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new UnExpectedException(MAErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
        }
    }

    public static String object2JsonPretty(Object object) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            throw new UnExpectedException(MAErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
        }
    }

    public static <T> T convert(Object object, Class<T> clazz) {
        try {
            return MAPPER.convertValue(object, clazz);
        } catch (Exception e) {
            throw new UnExpectedException(MAErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
        }
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            log.error(">>> Cannot convert {} to type {}", json, clazz);
            throw new UnExpectedException(MAErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
        }
    }

    public static Map<String, Object> json2Map(String json) {
        try {
            TypeReference<HashMap<String, Object>> typeRef
                    = new TypeReference<HashMap<String, Object>>() {
            };
            return MAPPER.readValue(json, typeRef);
        } catch (Exception e) {
            log.error(">>> Cannot convert {} to map", json);
            throw new UnExpectedException(MAErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
        }
    }

    public static <T> T byte2Object(byte[] data, Class<T> clazz) {
        try {
            return MAPPER.readValue(data, clazz);
        } catch (IOException e) {
            log.error(">>> Cannot convert {} to json", new String(data, StandardCharsets.UTF_8));
            throw new UnExpectedException(MAErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
        }
    }

    public static <T> List<T> byte2ListObject(byte[] data, Class<T> clazz) {
        String value = new String(data, StandardCharsets.UTF_8);
        return json2List(value, clazz);
    }

    public static <T> byte[] object2Byte(T object) {
        try {
            return MAPPER.writeValueAsBytes(object);
        } catch (IOException e) {
            log.error(">>> Cannot convert {} to byte", object);
            throw new UnExpectedException(MAErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
        }
    }

    public static <T> List<T> json2List(String jsonArray, Class<T> clazz) {
        try {
            return MAPPER.readValue(jsonArray, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            log.error(">>> Cannot convert {} to list of {}", jsonArray, clazz);
            throw new UnExpectedException(MAErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
        }
    }


    public static String object2JsonSafe(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return object2Json(obj);
    }

    public static String object2JsonSafePretty(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return object2JsonSafePretty(obj);
    }

    public static <T> T convertMap(Map<String, String> map, Class<T> clazz) {
        try {
            return MAPPER.convertValue(map, clazz);
        } catch (Exception e) {
            throw new UnExpectedException(MAErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
        }
    }
}
