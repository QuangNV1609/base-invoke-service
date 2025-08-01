package com.quangnv.msb.core.funtional;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.concurrent.Callable;

@UtilityClass
public class SafeValue {

    public static <T> T of(Callable<T> callable, T defaultValue) {
        Objects.requireNonNull(defaultValue);
        return Maybe.of(callable).orElse(defaultValue);
    }

    public static boolean ofBoolean(Callable<Boolean> callable) {
        return of(callable, false);
    }

    public static String ofString(Callable<String> callable) {
        return of(callable, "");
    }

    public static int ofInt(Callable<Integer> callable) {
        return of(callable, 0);
    }

    public static long ofLong(Callable<Long> callable) {
        return of(callable, 0L);
    }

    public static double ofDouble(Callable<Double> callable) {
        return of(callable, 0.0);
    }

    public static double ofFloat(Callable<Float> callable) {
        return of(callable, 0.0f);
    }
}