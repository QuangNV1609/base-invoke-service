package com.quangnv.msb.utils.validation.impl;

import com.quangnv.msb.utils.validation.AcceptChar;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AcceptCharValidator implements ConstraintValidator<AcceptChar, String> {
    private String name;
    private String regex;
    private String message;

    @Override
    public void initialize(AcceptChar acceptChar) {
        ConstraintValidator.super.initialize(acceptChar);
        this.name = acceptChar.name();
        this.regex = acceptChar.regex();
        this.message = acceptChar.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || Pattern.matches(this.regex, value)) {
            return true;
        }
        context.unwrap(HibernateConstraintValidatorContext.class)
                .addMessageParameter("0", this.name)
                .addMessageParameter("1", this.regex);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(this.message)
                .addConstraintViolation();
        return false;
    }
}
