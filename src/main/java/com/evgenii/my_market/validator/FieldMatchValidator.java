package com.evgenii.my_market.validator;

import org.apache.commons.beanutils.BeanUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String errorMessageName;

    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        errorMessageName = constraintAnnotation.errorMessage();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean toReturn = false;
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            toReturn = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception e) {
            System.out.println(e.toString());
        }

        if(!toReturn) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessageName).addPropertyNode(firstFieldName).addConstraintViolation();
        }
        return toReturn;

    }
}