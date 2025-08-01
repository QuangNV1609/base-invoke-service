package com.quangnv.msb.configuration.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum UserStatus {
    LOCK, ACTIVE, CLOSED;
    private static final Map<String, UserStatus> ENUM_MAP;

    static {
        Map<String, UserStatus> map = new ConcurrentHashMap<>();
        for (UserStatus instance : values()) {
            map.put(instance.name().toUpperCase(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static UserStatus get(String name) {
        return ENUM_MAP.get(name.toUpperCase());
    }
}
