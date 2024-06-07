package com.zawadzkia.springtodo.validator;

import org.springframework.security.util.FieldUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.firstField();
        secondFieldName = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            Object firstObj = FieldUtils.getFieldValue(obj, firstFieldName);
            Object secondObj = FieldUtils.getFieldValue(obj, secondFieldName);

            boolean isValid = firstObj != null && firstObj.equals(secondObj);

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(secondFieldName)
                        .addConstraintViolation();
            }

            return isValid;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}