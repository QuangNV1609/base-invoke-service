package com.quangnv.msb.utils.validation.impl;

import com.quangnv.msb.utils.validation.NotEmptyField;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class NotEmptyFieldValidator implements ConstraintValidator<NotEmptyField, Object> {
    private String name;
    private String message;

    @Override
    public void initialize(NotEmptyField constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.name = constraintAnnotation.name();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isEmpty = false;
        if (value == null) {
            isEmpty = true;
        } else if (value instanceof String) {
            String str = (String) value;
            isEmpty = StringUtils.isEmpty(str) || StringUtils.isBlank(str);
        } else if (value instanceof Collection) {
            isEmpty = CollectionUtils.isEmpty((Collection<?>) value);
        }
        if (isEmpty) {
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .addMessageParameter("0", name);

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation();
        }
        return !isEmpty;
    }
}
