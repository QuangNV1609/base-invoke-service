package com.quangnv.msb.core.funtional;

public interface ThrowingFunction<T, R> {
    R apply(T t) throws Exception;
}
