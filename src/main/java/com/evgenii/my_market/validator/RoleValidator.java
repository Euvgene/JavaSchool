package com.evgenii.my_market.validator;

import com.evgenii.my_market.entity.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.List;

/**
 * Custom role validator.
 *
 * @author Boznyakov Evgenii
 */
public class RoleValidator implements ConstraintValidator<ValidRole, Object> {

    List<Role> roles = Collections.singletonList(new Role(1L, "ROLE_USER"));

    /**
     * Implements the validation logic. The state of value must not be altered.
     * This method can be accessed concurrently, thread-safety must be ensured by the implementation.
     *
     * @param value   – object to validate
     * @param context – context in which the constraint is evaluated
     * @return false if value does not pass the constraint
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return roles.contains(value);
    }
}