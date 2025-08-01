package com.quangnv.msb.configuration.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class JwtProperty {
    @Value("${integrate.jwt-properties-key}")
    @NotEmpty
    private String key;
    @Positive
    private int accessTokenTtlInMinutes = 15;
    @Positive
    private int refreshTokenTtlMinutes = 1440;
}