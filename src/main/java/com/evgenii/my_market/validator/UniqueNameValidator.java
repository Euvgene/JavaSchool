package com.evgenii.my_market.validator;

import com.evgenii.my_market.service.api.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@NoArgsConstructor
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !userService.isNameAlreadyInUse(value);
    }
}