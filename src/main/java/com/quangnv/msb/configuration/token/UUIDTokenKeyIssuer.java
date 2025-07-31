package com.quangnv.msb.configuration.token;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDTokenKeyIssuer implements TokenKeyIssuer {
    @Override
    public String issue() {
        return UUID.randomUUID().toString();
    }
}
