package com.evgenii.my_market.validator;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom field match validator.
 *
 * @author Boznyakov Evgenii
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String errorMessageName;
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldMatchValidator.class);

    /**
     * Initializes the validator in preparation for isValid(Object, ConstraintValidatorContext) calls.
     * The constraint annotation for a given constraint declaration is passed.
     * This method is guaranteed to be called before any use of this instance for validation.
     * The default implementation is a no-op.
     *
     * @param constraintAnnotation – annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        errorMessageName = constraintAnnotation.errorMessage();
    }

    /**
     * Implements the validation logic. The state of value must not be altered.
     * This method can be accessed concurrently, thread-safety must be ensured by the implementation.
     *
     * @param value   – object to validate
     * @param context – context in which the constraint is evaluated
     * @return false if value does not pass the constraint
     */
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