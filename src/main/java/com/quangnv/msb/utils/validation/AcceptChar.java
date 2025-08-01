package com.quangnv.msb.utils.validation;


import com.quangnv.msb.utils.validation.impl.AcceptCharValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AcceptCharValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AcceptChar {
    String name();

    String message() default "{0013}";

    String regex();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}