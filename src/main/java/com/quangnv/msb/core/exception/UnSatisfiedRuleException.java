package com.quangnv.msb.core.exception;

import lombok.Getter;

@Getter
public class UnSatisfiedRuleException extends UnExpectedException {

    public UnSatisfiedRuleException(String code) {
        super(code);
    }
}
