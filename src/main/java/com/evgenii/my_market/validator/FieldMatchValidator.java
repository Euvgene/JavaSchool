package com.evgenii.my_market.validator;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String errorMessageName;
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldMatchValidator.class);

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        errorMessageName = constraintAnnotation.errorMessage();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean isPasswordsEquals = false;
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            isPasswordsEquals = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (Exception e) {
          LOGGER.info(e.getMessage());
        }

        if (!isPasswordsEquals) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessageName).addPropertyNode(firstFieldName).addConstraintViolation();
        }
        return isPasswordsEquals;

    }
}