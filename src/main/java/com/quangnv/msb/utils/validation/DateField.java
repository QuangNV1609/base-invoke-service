package com.quangnv.msb.utils.validation;


import com.quangnv.msb.utils.validation.impl.DateFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateFieldValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateField {
    String name();

    String message() default "{0012}";

    String pattern();

    String min() default "";

    String max() default "";
    boolean allowFuture() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
