package com.evgenii.my_market.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PaymentMethodValidator implements ConstraintValidator<ValidPaymentMethod, String> {

    List<String> methods = Arrays.asList("credit card", "cash");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return methods.contains(value);
    }
}