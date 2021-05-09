package com.evgenii.my_market.validator;

import com.evgenii.my_market.entity.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.List;

public class RoleValidator implements ConstraintValidator<ValidRole, Object> {

    List<Role> roles = Collections.singletonList(new Role(1L, "ROLE_USER"));

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return roles.contains(value);
    }
}