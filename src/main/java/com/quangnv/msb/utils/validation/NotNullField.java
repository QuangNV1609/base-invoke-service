package com.quangnv.msb.utils.validation;

import com.quangnv.msb.utils.validation.impl.NotNullFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotNullFieldValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullField {
    String name();

    String message() default "{0011}";

    String disabled() default "false";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
