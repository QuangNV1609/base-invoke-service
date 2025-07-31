package com.quangnv.msb.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnExpectedException extends RuntimeException {
    private final String code;

    private final transient Object data;

    public UnExpectedException(String code, Object data) {
        this.code = code;
        this.data = data;
    }

    public UnExpectedException(String code) {
        this(code,  null);
    }
}
