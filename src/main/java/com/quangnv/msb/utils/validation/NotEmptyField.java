package com.quangnv.msb.utils.validation;


import com.quangnv.msb.utils.validation.impl.NotEmptyFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotEmptyFieldValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyField {
    String name();

    String message() default "{0010}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
