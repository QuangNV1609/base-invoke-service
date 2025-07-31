package com.quangnv.msb.configuration.api;

import com.quangnv.msb.core.body.ApplicationRequestBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaFlow {
    ApiAction action();
    Class<? extends ApplicationRequestBody>[] payload() default {};
}
