package com.quangnv.msb.utils.validation.impl;

import com.quangnv.msb.utils.validation.NotNullField;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullFieldValidator implements ConstraintValidator<NotNullField, Object> {
    private String name;
    private String message;

    @Override
    public void initialize(NotNullField constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.name = constraintAnnotation.name();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isFieldNull = value == null;
        if (isFieldNull) {
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .addMessageParameter("0", name);

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation();
        }
        return !isFieldNull;
    }
}
