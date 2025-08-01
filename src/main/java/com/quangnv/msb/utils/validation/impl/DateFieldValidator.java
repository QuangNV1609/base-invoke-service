package com.quangnv.msb.utils.validation.impl;

import com.quangnv.msb.utils.validation.DateField;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFieldValidator implements ConstraintValidator<DateField, String> {
    private String name;
    private String min;
    private String max;
    private boolean allowFuture;
    private String pattern;
    private String message;

    @Override
    public void initialize(DateField constraintAnnotation) {
        this.name = constraintAnnotation.name();
        this.pattern = constraintAnnotation.pattern();
        this.message = constraintAnnotation.message();
        this.allowFuture = constraintAnnotation.allowFuture();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        try {
            LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern));
            if (!allowFuture && LocalDate.now().isBefore(date)) {
                return false;
            }
            if (StringUtils.isNotEmpty(min)) {
                LocalDate minDate = LocalDate.parse(min, DateTimeFormatter.ofPattern(pattern));
                if (date.isBefore(minDate)) {
                    return false;
                }
            }
            if (StringUtils.isNotEmpty(max)) {
                LocalDate maxDate = LocalDate.parse(max, DateTimeFormatter.ofPattern(pattern));
                if (date.isAfter(maxDate)) {
                    return false;
                }
            }
            return true;
        } catch (DateTimeParseException e) {
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .addMessageParameter("0", name)
                    .addMessageParameter("1", pattern);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation();
            return false;
        }
    }
}
