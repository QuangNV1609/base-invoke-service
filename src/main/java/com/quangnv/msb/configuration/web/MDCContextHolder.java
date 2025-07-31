package com.quangnv.msb.configuration.web;

import com.quangnv.msb.core.funtional.Maybe;
import com.quangnv.msb.core.utils.JsonHelpers;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.ThreadContext;

@UtilityClass
public class MDCContextHolder {

    public static void setMdc(String key, String value) {
        Maybe.of(value).peek(v -> ThreadContext.put(key, v));
    }

    public static void remove(String key) {
        Maybe.of(key).peek(ThreadContext::remove);
    }

    public static void setContext(String key, String value) {
        setMdc(key, value);
    }

    public static void setContext(MDCKey key, String value) {
        setContext(key.name(), value);
    }

    public static String get(MDCKey key) {
        return ThreadContext.get(key.name());
    }

    public static <T> void set(MDCKey key, T t) {
        String json = JsonHelpers.object2JsonSafe(t);
        MDCContextHolder.setContext(key, json);
    }

    public static <T> T get(MDCKey key, Class<T> clazz) {
        return Maybe.of(key).map(MDCContextHolder::get)
                .mapThrowingFunc(json -> JsonHelpers.json2Object(json, clazz))
                .orElseNull();
    }

    public static String getTokenKey() {
        return get(MDCKey.TK);
    }
    public static String getErrorTokenKey() {
        return get(MDCKey.ETK);
    }

    public static String getTid() {
        return get(MDCKey.TID);
    }

    public static String getMid() {
        return get(MDCKey.MID);
    }

    public static String getClientIP() {
        return get(MDCKey.CLIENT_IP);
    }

    public static String getRequestTime() {
        return get(MDCKey.RT);
    }
}
