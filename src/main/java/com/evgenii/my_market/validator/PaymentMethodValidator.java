package com.evgenii.my_market.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * Custom payment method validator.
 *
 * @author Boznyakov Evgenii
 */
public class PaymentMethodValidator implements ConstraintValidator<ValidPaymentMethod, String> {

    List<String> methods = Arrays.asList("credit card", "cash");

    /**
     * Implements the validation logic. The state of value must not be altered.
     * This method can be accessed concurrently, thread-safety must be ensured by the implementation.
     *
     * @param value   – object to validate
     * @param context – context in which the constraint is evaluated
     * @return false if value does not pass the constraint
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return methods.contains(value);
    }
}