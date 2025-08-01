package com.quangnv.msb.configuration.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Component
@ConfigurationProperties(value = "msb.security")
@Validated
public class SecurityProperties {
    @NotNull
    @NestedConfigurationProperty
    private JwtProperty jwt;
    private Set<String> permitAllActions = new HashSet<>();
    private List<String> permitAllUrls = new ArrayList<>();
    private List<String> corsPaths = new ArrayList<>();
}
